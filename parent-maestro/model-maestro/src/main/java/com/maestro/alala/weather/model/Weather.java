package com.maestro.alala.weather.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({@NamedQuery(name="Weather.byLocation",
  query="from Weather w where w.location = :location")})

public class Weather {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(cascade=CascadeType.ALL)
	private Location location;
	
	@OneToOne(mappedBy="weather", cascade=CascadeType.ALL)
	private Condition condition;
	
	@OneToOne(mappedBy="weather", cascade=CascadeType.ALL)
	private Wind wind;
	
	@OneToOne(mappedBy="weather", cascade=CascadeType.ALL)
	private Atmosphere atmosphere;
	
	private Date date;
	
	public Weather(){}
	
	public Integer getId(){ return id;	}
	public void setId() { this.id = id; }
	
}
