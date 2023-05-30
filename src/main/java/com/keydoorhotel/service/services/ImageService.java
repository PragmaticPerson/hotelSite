package com.keydoorhotel.service.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.keydoorhotel.exceptions.StorageException;
import com.keydoorhotel.service.model.Room;

@Service
public class ImageService {

	private Path rootLocation;
	private RoomService roomService;

	@Autowired
	public ImageService(RoomService roomService, @Value("${application.image.save.url}") String url) {
		super();
		rootLocation = Path.of(url);
		this.roomService = roomService;
	}

	public void saveImageForRoom(int roomId, MultipartFile file) {
		var room = roomService.findById(roomId);
		Path imageLocation = load(room.getType().getSource());

		store(imageLocation, file);

		changeImageCountInRoomEntity(room, 1);
	}

	public void deleteImageFromRoom(int roomId, String imageName) {
		var room = roomService.findById(roomId);
		Path imageLocation = load(room.getType().getSource() + "\\" + imageName);

		try {
			Files.delete(imageLocation);
		} catch (IOException e) {
			throw new StorageException("Failed to delete file.", e);
		}

		changeImageCountInRoomEntity(room, -1);
	}

	public InputStream loadImageBySourceAndName(String source, String name) throws IOException {
		var file = loadAsInputStream(source + "\\" + name);
		return file;
	}

	public List<InputStream> getAllImagesForRoom(String source) {
		var path = load(source);
		var resultList = new ArrayList<InputStream>();
		loadAll(path).forEach(p -> {
			resultList.add(loadAsInputStream(source + "\\" + p.getFileName()));
		});
		return resultList;
	}

	public List<String> getAllImageNames(String source) {
		var path = load(source);
		var resultList = new ArrayList<String>();
		loadAll(path).forEach(p -> {
			resultList.add(p.getFileName().toString());
		});

		return resultList;
	}

	private void changeImageCountInRoomEntity(Room room, int change) {
		var roomType = room.getType();
		roomType.setImageCount(room.getType().getImageCount() + change);
		room.setType(roomType);
		roomService.save(room);
	}

	private void store(Path imageLocation, MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = imageLocation.resolve(Paths.get(file.getOriginalFilename())).normalize()
					.toAbsolutePath();
			if (!destinationFile.getParent().equals(imageLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException("Cannot store file outside current directory.");
			}

			File checkIfDirectoryFile = destinationFile.toFile().getParentFile();
			if (!checkIfDirectoryFile.exists()) {
				checkIfDirectoryFile.mkdir();
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	private Stream<Path> loadAll(Path rootLocation) {
		try {
			return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation)).map(rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	private Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	private InputStream loadAsInputStream(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource.getInputStream();
			} else {
				throw new StorageException("Could not read file: " + filename);

			}
		} catch (IOException e) {
			throw new StorageException("Could not read file: " + filename, e);
		}
	}

	private void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	private void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
