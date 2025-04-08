package backend.spring.service.query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import backend.spring.dto.response.view.ViewHomeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import backend.spring.dto.object.ViewProjectDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.dto.response.view.ViewProjectResponseDto;
import backend.spring.entity.Project;

import backend.spring.entity.QProject;
import backend.spring.entity.QProjectStack;

import backend.spring.entity.ProjectStack;
import backend.spring.entity.User;
import backend.spring.entity.enums.Location;
import backend.spring.entity.enums.OrderProject;
import backend.spring.entity.enums.Stack;
import backend.spring.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewProjectService {

	private final EntityManager entityManager;
	private final JPAQueryFactory queryFactory;
	private final QProject qProject = QProject.project;

	private final UserRepository userRepository;

	/**
	 * 필터링 및 정렬
	 * @param page   //현재 페이지
	 * @param order //정렬
	 * @param process //모집중만 보여줄건지
	 * @param location //어느 위치
	 * @param stacks //어느 스택
	 * @param keyword //키워드 검색
	 * @return ViewProjectResponseDto
	 */
	public ResponseEntity<? super ViewProjectResponseDto> getProjectPage(Long user_id, int page, OrderProject order, boolean process, Location location, List<Stack> stacks, String keyword) {
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			User user = option.get(); //user를 받아서 recommend순 정렬

			// 필터링
			BooleanBuilder filterBuilder = ProjectQueryHelper.createFilterBuilder(process, location, stacks, keyword, qProject);
			List<Project> results;

			//정렬
			if(order == OrderProject.RECOMMEND){//추천 알고리즘
				results = queryFactory.selectFrom(qProject)
					.leftJoin(qProject.stackList, QProjectStack.projectStack).fetchJoin()
					.where(filterBuilder)
					.limit(6)
					.fetch();
				//필터링된 데이터를 넣어 추천 알고리즘 순으로 정렬 + 한 페이지에 들어갈 것만 가져오기
			} else{ //최근순, 인기순 정렬
				OrderSpecifier<?> orderSpecifier = ProjectQueryHelper.getOrderSpecifier(order, qProject); //정렬
				results = getFilteredSortedResults(page, orderSpecifier, filterBuilder); //필터링+정렬
			}

			// 결과 없으면 처리
			if (results == null || results.isEmpty()) {
				return ViewProjectResponseDto.zero_project();
			}

			// 필터링된 프로젝트 개수 조회 쿼리
			long totalCount = queryFactory.selectFrom(qProject)
				.where(filterBuilder)
				.fetchCount();

			List<ViewProjectDto> projectList = mapToViewProjectDto(results);
			return ViewProjectResponseDto.success(projectList, (int)totalCount ,page);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	public ResponseEntity<? super ViewHomeResponseDto> getHomePage(Long user_id){
		try{
			Optional<User> option = userRepository.findById(user_id);
			if(option.isEmpty()) {
				return ResponseDto.not_existed_user();
			}
			User user = option.get(); //user를 받아서 recommend순 정렬

			// 최근순, 인기순, 추천순 각각 상위 6개 프로젝트 가져옴
			List<Project> recent;
			List<Project> popular;
			List<Project> recommend;

			// 추천순 정렬
			recommend = queryFactory.selectFrom(qProject) //전체 프로젝트 가져옴
				.leftJoin(qProject.stackList, QProjectStack.projectStack).fetchJoin()
				.limit(6)
				.fetch();
			// 전체 프로젝트 가져와도 없으면 오류
			if (recommend == null || recommend.isEmpty()) {
				return ViewHomeResponseDto.zero_project();
			}
			//전체 프로젝트 넣어서 추천순으로 정렬후 상위 6개 가져옴
			List<ViewProjectDto> recommendList = mapToViewProjectDto(recommend);

			// 최근순 정렬
			OrderSpecifier<?> recentOrder = ProjectQueryHelper.getOrderSpecifier(OrderProject.RECENT, qProject);//정렬
			recent= queryFactory.selectFrom(qProject)
				.leftJoin(qProject.stackList, QProjectStack.projectStack).fetchJoin()
				.orderBy(recentOrder)
				.limit(6)
				.fetch();
			List<ViewProjectDto> recentList = mapToViewProjectDto(recent);

			// 인기순 정렬
			OrderSpecifier<?> popularOrder = ProjectQueryHelper.getOrderSpecifier(OrderProject.POPULAR, qProject);//정렬
			popular= queryFactory.selectFrom(qProject)
				.leftJoin(qProject.stackList, QProjectStack.projectStack).fetchJoin()
				.orderBy(popularOrder)
				.limit(6)
				.fetch();
			List<ViewProjectDto> popularList = mapToViewProjectDto(popular);

			return ViewHomeResponseDto.success(recentList, popularList, recommendList);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}

	// 필터링 및 정렬 적용
	private List<Project> getFilteredSortedResults(int page, OrderSpecifier orderSpecifier, BooleanBuilder filterBuilder) {
		return queryFactory.selectFrom(qProject)
			.leftJoin(qProject.stackList, QProjectStack.projectStack).fetchJoin() //ProjectStack과 조인
			.where(filterBuilder)
			.orderBy(orderSpecifier)
			.offset(page * 6)   //page*size: 가져오기 시작하는 인덱스
			.limit(6)
			.fetch();
	}
	// Product 리스트 -> ProductListDto 리스트로 변환 메서드
	public List<ViewProjectDto> mapToViewProjectDto(List<Project> projects) {
		return projects.stream()
			.map(p -> new ViewProjectDto(
				p.getProjectId(),
				p.getTitle(),
				p.getDescription(),
				p.getStackList().stream()
					.map(ProjectStack::getStack) //Stack만 뽑아냄
					.collect(Collectors.toList())
			))
			.collect(Collectors.toList());
	}


}