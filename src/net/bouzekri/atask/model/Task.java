/**
 * Copyright 2012 Jonathan Bouzekri <jonathan.bouzekri@gmail.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
package net.bouzekri.atask.model;

import java.io.Serializable;

public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
	
    private int id = 0;
    private String title;
    private String content;
    private long date = 0;
    
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

    public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setChecked(boolean checked) {
        this.checked = checked ;
    }

    public void toggleChecked() {
        checked = !checked ;
    }

    public boolean isChecked() {
        return checked;
    }
}
