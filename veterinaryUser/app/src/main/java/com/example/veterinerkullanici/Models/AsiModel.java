package com.example.veterinerkullanici.Models;

public class AsiModel{
	private boolean tf;
	private String hayvanisim;
	private String hayvanresim;
	private String asitarih;
	private String asiisim;
	private String hayvantur;
	private String hayvancins;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setHayvanisim(String hayvanisim){
		this.hayvanisim = hayvanisim;
	}

	public String getHayvanisim(){
		return hayvanisim;
	}

	public void setHayvanresim(String hayvanresim){
		this.hayvanresim = hayvanresim;
	}

	public String getHayvanresim(){
		return hayvanresim;
	}

	public void setAsitarih(String asitarih){
		this.asitarih = asitarih;
	}

	public String getAsitarih(){
		return asitarih;
	}

	public void setAsiisim(String asiisim){
		this.asiisim = asiisim;
	}

	public String getAsiisim(){
		return asiisim;
	}

	public void setHayvantur(String hayvantur){
		this.hayvantur = hayvantur;
	}

	public String getHayvantur(){
		return hayvantur;
	}

	public void setHayvancins(String hayvancins){
		this.hayvancins = hayvancins;
	}

	public String getHayvancins(){
		return hayvancins;
	}

	@Override
 	public String toString(){
		return 
			"AsiModel{" + 
			"tf = '" + tf + '\'' + 
			",hayvanisim = '" + hayvanisim + '\'' + 
			",hayvanresim = '" + hayvanresim + '\'' + 
			",asitarih = '" + asitarih + '\'' + 
			",asiisim = '" + asiisim + '\'' + 
			",hayvantur = '" + hayvantur + '\'' + 
			",hayvancins = '" + hayvancins + '\'' + 
			"}";
		}
}
