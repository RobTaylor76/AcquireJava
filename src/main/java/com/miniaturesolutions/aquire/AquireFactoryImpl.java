package com.miniaturesolutions.aquire;

import java.util.LinkedList;
import java.util.List;

public class AquireFactoryImpl implements AquireFactory {

	@Override
	public List<Corporation> createCorporations() {
		
		List<Corporation> coporations = new LinkedList<>();
        for(NamedCorporation def : NamedCorporation.values()) {
            if (def == NamedCorporation.UNINCORPORATED) {
                continue;
            }
        	Corporation corp = new Corporation(def);
        	coporations.add(corp);
        }
		return coporations; 
	}

	@Override
	public AquireBoard createBoard() {
		return new BoardImpl();
	}

}
