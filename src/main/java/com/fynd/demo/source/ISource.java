package com.fynd.demo.source;

public interface ISource<I,O> {
	public O getData();
	public void setData(I data);
}
