package com.fynd.demo.parser;

public interface IParser<I,O> {
	void setData(I input);
	void parse();
	O getData();
}
