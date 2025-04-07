package backend.spring.service.query;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;

import backend.spring.entity.QProject;
import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.OrderProject;
import backend.spring.entity.enums.Processing;
import backend.spring.entity.enums.Stack;

public class ProjectQueryHelper {

	/**
	 * 정렬 수행
	 * @param order 정렬 조건
	 */
	public static OrderSpecifier<?> getOrderSpecifier(OrderProject order, QProject project) {
		if (order == null) {
			// order가 null인 경우 최근순으로 처리
			return project.created_at.desc();
		}
		switch (order) {
			case RECENT:
				return project.created_at.desc();
			case POPULAR:
				return project.viewCount.desc();
			default:
				return project.created_at.desc();
		}
	}

	/**
	 * 필터링 수행
	 * @param process //모집중
	 * @param location //위치
	 * @param stacks //스택들
	 * @param keyword //키워드
	 */
	public static BooleanBuilder createFilterBuilder(boolean process, Location location, List<Stack> stacks, String keyword, QProject project) {
		BooleanBuilder filterBuilder = new BooleanBuilder();
		//filterBuilder가 함수를 거쳐감에 따라 필터링된다.
		// 검색
		addKeywordFilter(keyword, project, filterBuilder);
		// 위치 필터링
		addLocationFilter(location, project, filterBuilder);
		// 스택 필터링
		addStackFilter(stacks, project, filterBuilder);
		//모집중 필터링
		addProcessingFilter(process, project, filterBuilder);

		return filterBuilder;
	}

	// 모집중 필터링 메서드
	private static void addProcessingFilter(boolean process, QProject project, BooleanBuilder filterBuilder) {
		if (process) {
			filterBuilder.and(project.processing.eq(Processing.모집중));
		} else{  //모집중+모집완료
			filterBuilder.and(project.processing.in(Processing.모집중, Processing.모집완료));
		}
	}

	// 위치 필터링 메서드
	private static void addLocationFilter(Location location, QProject project, BooleanBuilder filterBuilder) {
		if (location != null) {
			filterBuilder.and(project.meet_location.eq(location));
		}
	}

	// 스택 필터링 메서드
	private static void addStackFilter(List<Stack> stacks, QProject project, BooleanBuilder filterBuilder) {
		if (stacks != null && !stacks.isEmpty()) {
			BooleanBuilder stackCondition = new BooleanBuilder();
			for (Stack stack : stacks) {
				// stackList들 중에 project의 스택이 하나라도 포함되면 그 project 포함
				stackCondition.or(project.stackList.any().stack.eq(stack));
			}
			filterBuilder.and(stackCondition);
		}
	}

	// 검색 메서드
	private static void addKeywordFilter(String keyword, QProject project, BooleanBuilder filterBuilder) {
		if (keyword != null) {
			filterBuilder.and(
				project.title.containsIgnoreCase(keyword)
					.or(project.description.containsIgnoreCase(keyword))
			);
		}
	}

}