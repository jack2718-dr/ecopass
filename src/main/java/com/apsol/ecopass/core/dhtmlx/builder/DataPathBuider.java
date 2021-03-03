package com.apsol.ecopass.core.dhtmlx.builder;

import com.apsol.ecopass.core.dhtmlx.model.path.BackPath;
import com.apsol.ecopass.core.dhtmlx.model.path.DataPath;
import com.apsol.ecopass.core.dhtmlx.model.path.FrontPath;
import com.apsol.ecopass.core.dhtmlx.model.path.TotalDataPath;
import com.querydsl.core.types.Expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataPathBuider {

	public TotalDataPath build(List<FrontPath>		frontPaths,
							   List<BackPath>		backPaths,
							   List<String>		frontIds,
							   int 				posStart,
							   int 				count 		) {
		
		List<String> backIds = new ArrayList<>();
		Map<String, Expression<?>> pathMap = new HashMap<>();
		List<String> expStringList = new ArrayList<>();
		
		if(frontPaths.size() != frontIds.size())
			throw new RuntimeException("<frontPaths>와 <frontIds>의 사이즈가 다릅니다.");
		
		for(FrontPath frontPath : frontPaths) {
			if(!frontIds.contains(frontPath.getId())) {
				throw new RuntimeException("<frontIds>에 <" + frontPath.getId() + ">가 존재하지 않습니다.");
			}
			setPathMap(pathMap, frontPath);
			expStringList.add(frontPath.getExp().toString());
		}
		
		if(0 < backPaths.size()) {
			for(BackPath backPath : backPaths) {
				setPathMap(pathMap, backPath);
				expStringList.add(backPath.getExp().toString());
				backIds.add(backPath.getId());
			}
		}
		
		if(frontPaths.size() + backPaths.size() != pathMap.size())
			throw new RuntimeException("<frontPaths>와 <backPaths>에 중복된 ID가 존재합니다.");
		
		deduplicateExpression(pathMap, expStringList);
		
		return new TotalDataPath(frontIds, backIds, posStart, count, pathMap);
	}
	
	private void setPathMap(Map<String, Expression<?>> pathMap, DataPath dataPath) {
		pathMap.put(dataPath.getId(), dataPath.getExp());
	}
	
	private void deduplicateExpression(Map<String, Expression<?>> pathMap, List<String> expStringList) {
		List<String> result = expStringList.stream().distinct().collect(Collectors.toList());
		
		if(result.size() != pathMap.size())
			throw new RuntimeException("<frontPaths>와 <backPaths>에 중복된 Expression이 존재합니다.");
	}

	
}
