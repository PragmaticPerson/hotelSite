package com.keydoorhotel.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class MobiscrollTimelineDTO {
	private LocalDateTime start;
	private LocalDateTime end;
	private String title;
	private int resource;

	public MobiscrollTimelineDTO() {
		super();
	}

	public MobiscrollTimelineDTO(LocalDateTime start, LocalDateTime end, String title, int resource) {
		super();
		this.start = start;
		this.end = end;
		this.title = title;
		this.resource = resource;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getResource() {
		return resource;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	@Override
	public int hashCode() {
		return Objects.hash(end, resource, start, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MobiscrollTimelineDTO other = (MobiscrollTimelineDTO) obj;
		return Objects.equals(end, other.end) && resource == other.resource && Objects.equals(start, other.start)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "MobiscrollDTO [start=" + start + ", end=" + end + ", title=" + title + ", resource=" + resource + "]";
	}

}
