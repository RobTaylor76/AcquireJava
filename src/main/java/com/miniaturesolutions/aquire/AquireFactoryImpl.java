package com.miniaturesolutions.aquire;

import java.util.LinkedList;
import java.util.List;

public class AquireFactoryImpl implements AquireFactory {

	@Override
	public List<CorporationImpl> createCorporations() {
		
		List<CorporationImpl> coporations = new LinkedList<>();
        for(Corporation def : Corporation.values()) {
            if (def == Corporation.UNINCORPORATED) {
                continue;
            }
        	CorporationImpl corp = new CorporationImpl(def);
        	coporations.add(corp);
        }
		return coporations; 
	}

	@Override
	public Board createBoard() {
		return new Board();
	}

}
