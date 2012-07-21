package net.bouzekri.atask.model;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
    private String title;
    private String content;
    private boolean checked = false;
    
    public Task() {
    }
    
    public Task(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    public Task(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public void toggleChecked() {
        checked = !checked ;
    }

	public boolean isChecked() {
        return checked;
    }
    
}
