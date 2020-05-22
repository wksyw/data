package com.gis.loader;

import java.io.IOException;
import java.util.*;

public interface LineParser {
	public abstract void parseLine(String line) throws IOException,
			NumberFormatException, NoSuchElementException;
}
