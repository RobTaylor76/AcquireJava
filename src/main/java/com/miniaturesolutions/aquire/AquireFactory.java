package com.miniaturesolutions.aquire;

import java.util.List;

public interface AquireFactory {
	List<Corporation> createCorporations();
	AquireBoard createBoard();
}
