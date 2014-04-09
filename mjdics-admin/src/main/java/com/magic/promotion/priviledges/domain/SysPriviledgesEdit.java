package com.magic.promotion.priviledges.domain;

import java.util.ArrayList;
import java.util.List;

import com.magic.promotion.util.enumUtil.MenuTypeEnum;

public class SysPriviledgesEdit {
    private Integer id;

    private String text;

    private String url;

    private MenuTypeEnum folder;

    private MenuTypeEnum menu;

    private MenuTypeEnum anonymous;

    private Integer sequency;

    private Integer _parentId;

    private boolean checked = false;
    
    private String state;
    
    public String getState() {
    	if(folder==MenuTypeEnum.Y)
    		state = "closed";
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }


    public MenuTypeEnum getFolder() {
		return folder;
	}

	public void setFolder(MenuTypeEnum folder) {
		this.folder = folder;
	}


    public MenuTypeEnum getMenu() {
		return menu;
	}

	public void setMenu(MenuTypeEnum menu) {
		this.menu = menu;
	}

	public MenuTypeEnum getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(MenuTypeEnum anonymous) {
		this.anonymous = anonymous;
	}

	public Integer getSequency() {
        return sequency;
    }

    public void setSequency(Integer sequency) {
        this.sequency = sequency;
    }

	public Integer get_parentId() {
		return _parentId;
	}

	public void set_parentId(Integer _parentId) {
		this._parentId = _parentId;
	}


	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}





}