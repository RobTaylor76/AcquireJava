package com.miniaturesolutions.aquire;

import java.util.List;

public interface AquireFactory {
	List<CorporationImpl> createCorporations();
	Board createBoard();
}
