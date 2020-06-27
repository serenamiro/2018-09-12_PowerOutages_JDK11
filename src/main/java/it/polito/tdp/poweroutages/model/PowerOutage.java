package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.util.Date;

public class PowerOutage {
	
	private Integer id;
	private Nerc nercId;
	private LocalDateTime date_event_began;
	private LocalDateTime date_event_finished;
	
	public PowerOutage(Integer id, Nerc nercId, LocalDateTime date_event_began, LocalDateTime date_event_finished) {
		super();
		this.id = id;
		this.nercId = nercId;
		this.date_event_began = date_event_began;
		this.date_event_finished = date_event_finished;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Nerc getNercId() {
		return nercId;
	}
	public void setNercId(Nerc nercId) {
		this.nercId = nercId;
	}
	public LocalDateTime getDate_event_began() {
		return date_event_began;
	}
	public void setDate_event_began(LocalDateTime date_event_began) {
		this.date_event_began = date_event_began;
	}
	public LocalDateTime getDate_event_finished() {
		return date_event_finished;
	}
	public void setDate_event_finished(LocalDateTime date_event_finished) {
		this.date_event_finished = date_event_finished;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
