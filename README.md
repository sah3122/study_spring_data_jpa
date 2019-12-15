# study_spring_data_jpa
Inflearn Spring Data JPA 강의 정리

* 관계형 데이터 베이스와 자바
    * JDBC
        * DataSource / DriverManager
        * Connection
        * PreparedStatement
    * SQL
        * DDL
        * DML
    * 무엇이 문제인가 ?
        * SQL을 실행하는 비용이 비싸다.
        * SQL 이 데이터베이스 마다 다르다.
        * 스키마를 바꿧더니 코드의 변경이 많다.
        * 반복적인 코드가 많다.
        * 불필요한 데이터를 다 읽어와야 하는지 ?
* ORM 개요
    * JDBC 대신 도메인 모델을 사용하려는 이유 ?
        * 객체지향 프로그래밍의 장점을 활용하기 좋으니까
        * 각종 디자인 패턴 사용가능
        * 코드 재사용
        * 비즈니스 로직 구현 및 테스트 편함.
    * ORM은 애플리케이션의 클래스와 SQL 데이터베이스의 테이블 사이의 맵핑 정보를 기술한 메타데이터를 사용하여 자바 애플리케이션의 객체를 SQL 데이터 베이스의 테이블에 자동으로 (깨끗하게) 영속화 해주는 기술.
* ORM 패러다임 불일치
    * 객체를 릴레이션에 맵핑하려니 발생하는 문제들과 해결책
        * 밀도 문제
            * 객체
                * 다양한 크기의 객첼르 만들 수 있음
                * 커스텀한 타입 만들기 쉬움
            * 릴레이션
                * 테이블
                * 기본 데이터 타입
        * 서브타입 문제
            * 객체
                * 상속 구조 만들기 쉬움
                * 다형성
            * 릴레이션
                * 테이블 상속이라는 개념이 없음
                * 상속 기능을 구현했다 하더라도 표준 기술이 아님
                * 다형적인 관계를 표현할 방법이 없음
        * 식별성 문제
            * 객체
                * 레퍼런스 동일성 ==
                * 인스턴스 동일성 equals
            * 릴레이션
                * 주 키(primary key)
        * 관계 문제
            * 객체
                * 객체 레퍼런스로 관계 표현
                * 근본적으로 방향이 존재한다.
                * 다대다 관계를 가질 수 있음.
            * 릴레이션
                * 외래키로 관계 표현
                * 방향이라는 의미가 없음, 그냥 join으로 아무거나 묶을 수 있음
                * 태생ㅈ거으로 다대다 관계를 못 만들고 조인 테이블또는 링크 테이블을 사용해서 두개의 1대다 관계로 풀어야 함
        * 데이터 네비게이션 문제
            * 객체
                * 레퍼런스를 이용해서 다른 객체로 이동 가능
                * 콜렉션을 순회 할 수 있음
            * 릴레이션
                * 릴레이션에서 데이터를 조회하는데 있어 비효율적
                * 데이터베이스에 요청을 적게 할 수록 성능이 좋아 join을 사용함
                * 너무 많은 데이터를 가지고 오려는것도 문제
                * 그렇다고 lazy loading을 하자니 그것도 문제 (n + 1 select)
* JPA 프로그래밍
    * 프로젝트 세팅
        * 스프링 부트
            * 스프링 부트 v2.*
            * 스프링 프레임워크 v5.*
        * 스프링 부트 스타터 JPA
            * JPA 프로그래밍에 필요한 의존성 추가
                * JPA v2.*
                * Hibernate v5.*
            * 자동 설정 : HibernateeJpaAutoConfiguration
                * 컨테이너가 관리하는 EntityManager (프록시) 빈 설정
                * PlatfromTransactionManager 빈 설정
    * 엔티티 맵핑
        * @Entity 
            * 엔티티는 객체 세상에서 부르는 이름
            * 보통 클래스와 같은 이름을 사용하기 때문에 값을 변경하지 않음
            * 엔티티의 이름은 JQL에서 쓰임
        * @Table 
            * 릴레이션 세상에서 부르는 이름
            * @Entity의 이름이 기본값
            * 테이블의 이름은 SQL에서 쓰임
        * @Id
            * 엔티티의 주키를 맵핑할 때 사용
            * 자바의 모든 primitive 타입과 그 랩퍼 타입을 사용할 수 있음
                * Date, BigDecimal, BigInteger 사용 가능
            * 복합키를 만드는 방법도 있지만 논외
        * @GeneratedValue
            * 주키의 생성 방법을 맵핑하는 애노테이션
            * 생성 전략과 생성기를 설정할 수 있다.
                * 기본 전략은 AUTO : 사용하는 DB에 따라 적절한 전략 선택
                * TABLE, SEQUENCE, IDENTITY 중 하나
        * @Column
            * unique
            * nullable
            * length
            * columnDefinition
        * @Temporal 
            * 현재 JPA 2.1 까지는 Date와 Calender 지원
            * 2.2 부터 Java 8 Date 객체 사용가능
        * @Transient
            * 컬럼으로 맵핑하고 싶지 않은 멤버 변수에 적용
        * applciation.properties 추천
            * spring.jpa.show-sql=true
            * spring.jpa.properties.hibernate.format_sql=true
        * @Enumernate
            * 맵핑시 기본값이 아닌 String으로 사용해야 한다.        
    * Value 타입 맵핑
        * 엔티티 타입과 Value 타입 구분
            * 식별자가 있어야 하는가
            * 독립적으로 존재해야 하는가
        * Value 타입 종류
            * 기본 타입 (String, Date, Boolean, ...)
            * Composite Value 타입
            * Collection Value 타입
                * 기본 타입의 콜렉션
                * 컴포짓 타입의 콜렉션
        * Composite Value 타입 맵핑
            * @Embeddable
            * @Embedded
            * @AttributeOverrides
            * @AttributeOverride
    * 관계 맵핑
        * 관게에는 항상 두 엔티티가 존재
            * 둘 중 하나는 관계의 주인(owner)
            * 다른 쪽은 종속된(non-owning) 
            * 해당 관계의 반대쪽 레퍼런스를 가지고 있는 쪽이 주인
        * 단방향에서의 관계의 주인은 명확
            * 관계를 정의한 쪽이 그 관게의 주인
        * 단방향 @ManyToOne
            * 기본값은 FK 생성
        * 단방향 @OneToMany
            * 기본값은 조인 테이블 생성
        * 양방향
            * FK를 가지고 있는 쪽이 오너, 따라서 기본값은 @ManyToOne 가지고 있는 쪽이 주인
            * 주인이 아닌쪽 (@OneToMany)에서 mappedBy를 사용해서 관계를 맺고 있는 필드를 설정 해야 한다.
        * 양방향
            * @ManyToOne (이쪽이 주인)
            * @OneToMany (mappedBy)
            * 주인한테 관계를 설정해야 DB에 반영     
    * Cascade
        * 엔티티의 상태 변화를 전파 시키는 옵션
            * Transient : JPA가 모르는 상태
            * Persistent : JPA가 관리중인 상태 (1차 캐시, Dirty Checking, Write Behind, ...)
            * Detached : JPA가 더이상 관리하지 않는 상태
            * Removed : JPA가 관리하긴 하지만 삭제하기로 한 상태
    * Fetch
        * 연관 관계의 엔티티를 어떻게 가지고 올 것이냐 지금 (Eager) 나중에 (Lazy)
            * @OneToMany의 기본값은 Lazy
            * @ManyToOne의 기본값은 Eager
    * Query
        * JPQL (HQL)
            * Java Persistence Query Language / Hibernate Query Language
            * 데이터베이스 테이블이 아닌, 엔티티 객체 모델 기반으로 쿼리 작성
            * JPA 또는 하이버네이트가 해당 쿼리를 SQL로 변환해서 실행
        * Criteria
            * 타입 세이프 쿼리
        * Native Query
            * SQL 쿼리 실행하기
    
* 스프링 데이터 JPA 소개 및 원리
    * JpaRepository<Entity, id> 인터페이스
        * 매직 인터페이스
        * @Repository가 없어도 빈으로 등록해 줌.
    * @EnableJpaRepositories
        * 매직의 시작은 여기서 부터
    * 매직은 어떻게 이뤄지나 ?
        * 시작은 @Import(JpaRepositoriesRegistrar.class)
        * 홱심은 ImportBeanDefinitionRegistrar 인터페이스           
* 핵심 개념 이해 정리
    * 데이터베이스와 자바
    * 패러다임 불일치
    * ORM이란 ?
    * JPA 사용법 (엔티티, 벨류 타입, 관계 맵핑)
    * JPA 특징(엔티티 상태 변화, Cascade, Fetch, 1차 캐시, ...)
    * 주의할 점
        * 반드시 발생하는 SQL을 확인할 것
        * 팁
            * logging.level.org.hibernate.SQL=debug
            * logging.level.org.hibernate.type.description.sql=trace  // 파라미터 값 확인 할 수 있음
* 스프링 데이터 Common : Repository
    * 스프링 데이터 Common
        * Repository
        * CrudRepository
        * PagingAndSortingRepository
    * 스프링 데이터 JPA
        * JpaRepository
* 스프링 데이터 Common : 인터페이스 정의하기
    * Repository 인터페이스로 공개할 메소드를 직접 일일히 정의하고 싶다면 특정 리포지토리당
        * @RepositoryDefinition
    * 공통 인터페이스 정의
        * @NoRepositoryBean
* 스프링 데이터 Common : Null 처리하기
    * 스프링 데이터 2.0 부터 자바 8의 Optional 지원
        * Optional<Post> findById(Long id)
    * 콜렉션은 Null을 리턴하지 않고 비어있는 콜렉션을 리턴합니다.
    * 스프링 프레임워크 5.0부터 지원하는 Null 애노테이션 지원
        * @NonNull, @NonNullApi, @Nullable
        * 런타임 체크 지원 함
        * JSR 305 애노테이션을 메타 애노테이션으로 가지고 있음
    * 인텔리 J 설정
        * Buildm Execution, Deployment
            * Compiler
                * Add runtime assertion for notnull-annotated methods and parameters
* 스프링 데이터 Common : 쿼리 만들기 개요
    * 스프링 데이터 저장소의 메소드 이름으로 쿼리 만드는 방법
        * 메소드 이름을 분석해서 쿼리 만들기(CREATE)
        * 미리 정의해 둔 뭐리 찾아 사용하기(USE_DECLATED_QUERY)
        * 미리 정의한 쿼리 찾아보고 없으면 만들기 (CREATE_IF_NOT_FOUND)
    * 쿼리 만드는 방법
        * 리턴타입 {접두어}{도입부}By{프로퍼티 표현식}{조건식}[{And|OR}{프로퍼티표현식}{조건식}]{정렬조건}{매개변수}
        * 접두어
            * Find, Get, Query, Count, ...
        * 도입부
            * Distinct, First(N), Top(N)
        * 프로퍼티 표현식
            * Person, Address, ZipCode => find{Person}ByAddress_ZipCode(...)
        * 조건식
            * IgnoreCase, Between, LessThan, GreaterThan, Like, Contains, ...
        * 정렬 조건
            * OrderBy{프로퍼티}Asc|Desc
        * 리턴 타입
            * E, Optional<E>, List<E>, Page<E>, Slice<E>, Stream<E>, ...
        * 매개변수
            * Pageable, Sort
    * 쿼리 찾는 방법
        * 메소드 이름으로 쿼리를 표현하기 힘든 경우에 사용
        * 저장소 기술에 따라 다름
        * JPA : @Query, @ @NamedQuery
* 스프링 데이터 Common : 쿼리 만들기 실습
    * 기본예제
        * List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
        * // distinct
            * List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
        * List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
        * // ignoring case
            * List<Person> findByLastnameIgnoreCase(String lastname);
        * // ignoring case
            * List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
    * 정렬
        * List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
        * List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
    * 페이징
        * Page<User> findByLastname(String lastname, Pageable pageable);
        * Slice<User> findByLastname(String lastname, Pageable pageable);
        * List<User> findByLastname(String lastname, Sort sort);
        * List<User> findByLastname(String lastname, Pageable pageable);
    * 스트림
        * Stream<User> readAllByFirstnameNotNull();
        <br>
        try-with-resource 사용할 것. (Stream을 다 쓴다음에 close() 해야 함)
    * 비동기 쿼리
        * @Async Future<User> findByFirstname(String firstname);               
        * @Async CompletableFuture<User> findOneByFirstname(String firstname); 
        * @Async ListenableFuture<User> findOneByLastname(String lastname);
            * 해당 메소드를 스프링 TashExecutor에 전달해서 별도의 쓰레드에서 실행함.
            * Reactive랑 다름
        * 권장하지 않는 이유
            * 테스트 코드 작성이 어려움
            * 코드 복잡도 증가
            * 성능상 이점이 없다.
                * DB부하는 결국 같고
                * 메인 쓰레드 대신 백그라운드 쓰레드가 일하는 정도의 차이
                * 단, 백그라운드로 실행하고 결과를 받을 필요가 없는 작업이라면 @Async를 사용해서  응답속도를 향상 시킬수는 있다. 
* 스프링 데이터 Common : 커스텀 리포지토리
    * 쿼리 메소드(쿼리 생성과 쿼리 찾아쓰기)로 해결이 되지 않는 경우 직접 코딩으로 구현 가능.
        * 스프링 데이터 리포지토리 인터페이스에 기능 추가.
        * 스프링 데이터 리포지토리 기본 기능 덮어쓰기 가능.
        * 구현 방법
            * 커스텀 리포지토리 인터페이스 정의 
            * 인터페이스 구현 클래스 만들기(기본 접미어는 Impl)
            * 엔티티 리포지토리에 커스텀 리포지토리 인터페이스 추가
* 스프링 데이터 Common : 기본 리포지토리 커스터마이징
    * 모든 리포지토리에 공통적으로 추가하고 싶은 기능이 있거나 덮어쓰고 싶은 기본 기능이 있다면
        * JpaRepository를 상속 받는 인터페이스 정의
            * @NoRepositoryBean
        * 기본 구현체를 상속 받는 커스텀 구현체 만들기
        * @EnableJpaRepositories 에 설정
            * repositoryBaseClass
        ```
        @NoRepositoryBean
        public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
        
          boolean contains(T entity);
        
        }
        ```
        ```
        public class SimpleMyRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyRepository<T, ID> {
    
            private EntityManager entityManager;
        
            public SimpleMyRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
                super(entityInformation, entityManager);
                this.entityManager = entityManager;
            }
        
            @Override
            public boolean contains(T entity) {
                return entityManager.contains(entity);
            }
        }
        ```
        ```
        @EnableJpaRepositories(repositoryBaseClass = SimpleMyRepository.class)
        ```   
        ```
        public interface PostRepository extends MyRepository<Post, Long> {
        }
        ```   
* 스프링 데이터 Common : 도메인 이벤트
    * 도메인 관련 이벤트를 발생 시키기
    * 스프링 프레임워크의 이벤트 관련 기능
        * ApplicationContext extends ApplicationEventPublisher
        * 이벤트 : extends ApplicationEvent
        * 리스너
            * Implements ApplicationListener<E extends ApplicationEvent>
            * @EventListener
    * 스프링 데이터의 도메인 이벤트 Publisher
        * @DomainEvents
        * @AfterDomainEventPublisher
        * extends AbstractAggregationRoot<E>
        * 현재는 save() 할 때만 발생
* 스프링 데이터 Common : QueryDSL
    * 여러 쿼리 메소드는 대부분 두 가지 중 하나
        * Optional<T> findOne(Predicate) : 이런 저런 조건으로 무언가를 하나 찾는다.
        * List<T> | Page<T> | ... findAll(Predicate) : 이런 저런 조건으로 무언가 여러개 찾는다.
    * QueryDSL 
        * http://www.querydsl.com/
        * 타입 세이프한 쿼리를 만들 수 있게 도와주는 라이브러리
        * JPA, SQL, MongoDB, JDO, Lucene, Collection 지원
    * 스프링 데이터 JPA + QueryDSL
        * 인터페이스 : QuerydslPredicateExecutor<T>
        * 구현체 : QuerydslPredicateExecutor<T>
* 스프링 데이터 웹 지원 기능 설정
    * 스프링 부트를 사용하는 경우에 설정할 것이 없음(자동설정)
    * 스프리 부트를 사용하지 않는 경우엔 @EnableSpringDataWebSupport
    * 제공하는 기능
        * 도메인 클래스 컨버터
        * @RequestHandler 메소드에서 Pageable과 Sort 매개변수 사용
        * Page 관련 HATEOAS 기능 제공
            * PagedResourcesAssembler
            * PagedResource
        * Payload 프로덕션
            * 요청으로 들어오는 데이터 중 일부만 바인딩 받아오기
            * @ProjectedPayload, @XBRead, @JsonPath
        * 요청 쿼리 매개변수를 QueryDSLdml Predicate로 받아오기
            * ?firstname=Mr&lastname=White => Predicate
* 스프링 데이터 Common : Web 2부 DomainClassConverter
    * 스프링 Converter 
        * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/converter/Converter.html
        * Converter는 하나의 타입을 다른 타입으로 변환 해주는것.
        * Formatter 는 문자열 기반임. 어떠한 문자열을 어떤 타입으로 변환 해주는것.
* 스프링 데이터 Common : Web 2부 : Pageable 과 Sort 매개변수
    * 스프링 MVC HandlerMethodArgumentResolver
        * 스프링 MVC 핸들러 메소드의 매개변수로 받을 수 있는 객체를 확장하고 싶을 때 사용하는 인터페이스
        * https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodArgumentResolver.html
    * 페이징과 정렬 관련 매개변수
        * page : 0부터 시작
        * size : 기본값 20
        * sort : property.property(.ASC|DESC)
        * ex) sort = created.desc&dort=title (asc가 기본값)
* 스프링 데이터 Common : Web 4부 : HATEOAS
    * Page를 PagedResource 로 변환 - > 버전이 올라서 PagedResource가 아닌 PagedModel을 사용해야한다.
        * HATEOAS 의존성 추가
        * 핸들러 매개변수로 PagedResourcesAssembler 추가
* 스프링 데이터 JPA : JPA Repository
    * @EnableJpaRepositories
        * 스프링 부트 사용할 때는 사용하지 않아도 자동 설정 됨.
        * 스프링 부트 사용하지 않을 때는 @Configuration과 같이 사용
    * @Repository 애노테이션을 붙여야 하나 말아야 하나...
        * 안붙여도 된다.
        * SimpleJpaRepository 에 선언되어 있다.
    * 스프링 @Repository
        * SQLException 또는 JPA 관련 예외를 스프링의 DataAccessException으로 변환 해준다.
* 스프링 데이터 JPA : 엔티티 저장하기
    * JpaRepository의 save()는 단순히 새 엔티티를 추가하는 메소드가 아니다.
        * Transient 상태의 객체라면 EntityManager.persist();
            * Transient : PersistContext에 관리를 받지않은 상태
        * Detached 상태의 객체라면 EntityManager.merge();
            * Detached : PersistContext에 관리를 받은적이 있는 상태
    * Transient인지 Detached 인지 어떻게 판단하는가 ?
        * 엔티티의 @Id 프로퍼티를 찾는다. 해당 프로퍼티가 null이면 Transient 상태로 판단하고 id가 null이 아니면 Detached 상태로 판단한다.
        * 엔티티가 Persistable 인터페이스를 구현하고 있다면 isNew() 메소드에 위임한다.
        * JpaRepositoryFactory를 상속받는 클래스를 만들고 getEntityInfomation() 을 오버라이딩 해서 자신이 원하는 판단로직을 구현할 수도 있다.
        
    * EntityManager.persist()
        * Persist()메소드에 넘긴 그 엔티티 객체를 Persistent상태로 변경.
    * EntityManager.merge()
        * Merge() 메소드에 넘긴 그 엔티티의 복사본을 만들고, 그 복사본을 다시 Persisent상태로 변경하고 그 복사본을 반환.
        * merge 상태에는 파라미터로 전달한 객체를 영속화 하지 않는다. **항상 리턴 받는 객체를 사용하자.**  
* 스프링 데이터 JPA : 쿼리 메소드
    * 쿼리 생성하기
        * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
        * And, Or
        * Is, Equals
        * LessThan, LessThanEqual, GreaterThan, GreaterThanEqual
        * After, Before
        * IsNull, IsNotNull, NotNull
        * Like, NotLike
        * StartingWith, EndingWith, Containing
        * OrderBy
        * Not, In, NotIn
        * True, False
        * IgnoreCase
    * 쿼리 찾아쓰기
        * 엔티티에 정의한 쿼리 찾아 사용하기 JPA Named 쿼리
            * @NamedQuery
            * @NamedNativeQuery
        * 리포지토리 메소드에 정의한 쿼리 사용하기
            * @Query
            * @Query(nativeQuery=true) 
* 스프링 데이터 JPA : 쿼리 메소드 Sort
    * 이전과 마찬가지로 Pageable이나 Sort 매개변수로 사용할 수 있는데 @Query와 같이 사용할 때 제약사항이 있다.
    * Order by 절에서 함수를 호출 하는 경우에는 Sort를 사용하지 못한다. 그경우엔 JpaSort.unsafe()를 사용 해야 한다.
        * Sort는 그 안에서 사용한 **프로퍼티** 또는 **alias**가 엔티티에 없는 경우 예외를 던진다.
        * JpaSort.unsafe()를 사용하면 함수 호출을 할 수 있다.
            * JpaSort.unsafe("LENGTH(name)");
* 스프링 데이터 JPA : Named Parameter 와 SpEL
    * Named Parameter 
        * @Query에서 참조하는 매개변수를 ?1, ?2 이렇게 채번으로 참조하는게 아니라 이름으로 :title 이렇게 참조하는 방법은 다음과 같습니다.
        ```java
          @Query("SELECT p FROM Post AS p WHERE p.title = :title")
          List<Post> findByTitle(@Param("title") String title, Sort sort);
        ``` 
    * SqEL
        * 스프링 표현 언어
        * @Query에서 엔티티 이름을 #{#entityName} 으로 표현할 수 있다.
        ```java
          @Query("SELECT p FROM #{#entityName} AS p WHERE p.title = :title")
          List<Post> findByTitle(@Param("title") String title, Sort sort);
        ``` 
* 스프링 데이터 JPA : Update 쿼리 메소드
    * update, delte 쿼리 직접 정의하기
        * @Modifying @Query
        * 추천하지 않음.
* 스프링 데이터 JPA : EntityGraph
    * 쿼리 메소드 마다 연관 관계의 Fetch모드를 설정 할 수 있다.
    * @NamedEntityGraph
        * @Entity에서 재사용할 여러 엔티티 그룹을 정의 할 때 사용
    * @EntityGraph
        * @NamedEntityGraph에 정의 되어 있는 엔티티 그룹을 사용 함
        * 그래프 타입 설정 가능
            * (기본값) FETCH : 설정한 엔티티 애트리뷰트는 EAGER 패치 나머지는 LAZY 패치.
            * LOAD : 설정한 엔티티 애트리뷰트는 EAGER패치 나머지는 기본 패치 전략을 따름.
* 스프링 데이터 JPA : Projection
    * 엔티티의 일부 데이터만 가져오기
    * **인터페이스 기반** 프로젝션
        * Nested 프로젝션 기능.
        * **Closed** 프로젝션
            * 쿼리를 최적화 할 수 있다. 가져오려는 애트리뷰트가 뭔지 알고 있으니깐.
            * Java 8의 디폴트 메소드를 사용해서 연산을 할 수 있다.
        * Open 프로젝션
            * @Value(SqEL)을 사용해서 연산을 할 수 있다. 스프링 빈의 메소드도 호출 가능
            * 쿼리 최적화를 할 수 없다. SqEL을 엔티티 대상으로 사용하기 때문
    * 클래스 기반 프로젝션
        * DTO
        * 롬복 @Value로 코드 줄일 수 있음.
    * 다이나믹 프로젝션
        * 프로젝션 용 메소드 하나만 정의하고 실제 프로젝션 타입은 타입 인자로 전달하기.
        ```java
          <T> List<T> findByPost_Id(Long id, Class<T> type);
        ```
* 스프링 데이터 JPA : Specifications
    * 에릭 에반스의 책 DDD에서 언급하는 Specification 개념을 차용한 것으로 QueryDSL의 Predicate와 비슷
    * 설정 하는 방법
        * https://docs.jboss.org/hibernate/stable/jpamodelgen/reference/en-US/html_single/
        * 의존성 설정
        * 플러그인 설정
        * IDE에 애노테이션 처리기 설정 (org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor)
        * 코딩 시작.
        * 괜찮은 방법중 하나.
* 스프링 데이터 JPA : Query By Example 
    * QBE는 필드 이름을 작성할 필요 없이(뻥) 단순한 인터페이스를 통해 동적으로 쿼리를 만드는 기능을 제공하는 사용자
    친화적인 쿼리 기술. 
    * Example = Probe + ExampleMatcher
        * Probe는 필드에 어떤 값들을 가지고 있는 도메인 객체
        * ExampleMatcher는 Prove에 들어있는 그 필드의 값들을 어떻게 쿼리할 데이터와 비교할 지 정의한것
        * Example은 그 둘을 하나로 합친 것, 이걸로 쿼리를 함
    * 장점       
        * 별다른 코드 생성기나 애노테이션 처리기 필요 없음
        * 도메인 객체 리팩토링 해도 기존 쿼리가 깨질 걱정을 하지 않아도 됨. (뻥)
        * 데이터 기술에 독립적인 API
    * 단점
        * nested 또는 프로퍼티 그룹 제약 조건을 못 만든다.
        * 조건이 제한적이다. 문자열은 starts/contains/ends/regex가 가능하고 그 밖에 property는 값이 정확히 일치해야 한다.
    * Repository에 QueryByExampleExecutor 상속 
* 스프링 데이터 JPA : Transaction 
    * Isolation
        * 여러 트랜젝션이 동시에 DB에 접근할 때 어떻게 처리할 지 정의 
            * Default
            * Read_committed
            * Read_uncommitted
            * Repeatable_read
            * serializable
    * Propagation
        * 트랜젝션을 어떤방식으로 전파 시킬 것인지 정의. (Nested Transaction 에 관한 이야기.)
            * Mandatory
            * Nested
            * Never
            * Not_supported
            * Required
            * Requires_new
            * Supports
* 스프링 데이터 JPA : Auditing
    ```java
          @CreatedDate
          private Date created;
      
          @CreatedBy
          @ManyToOne
          private Account createdBy;
      
          @LastModifiedDate
          private Date updated;
      
          @LastModifiedBy
          @ManyToOne
          private Account updatedBy;
    ```
    * 엔티티의 변경 시점에 언제, 누가 변경 했는지에 대한 정보를 기록하는 기능.
    * 아쉽지만 이 기능은 스프링 부트가 자동설정해주지 않음.   
        1. 메인 애플리케이션 위에 @EnableJpaAuditing 추가
        2. 엔티티 클래스에 @EntityListener(AuditingEntityListener.class) 추가
        3. AuditorAware 구현체 만들기
        4. @EnableJpaAuditing에 AuditorAware 빈 이름 설정
        
    * JPA 라이프 사이클을 사용하면 조금더 General하게 설정 할 수 있다.
        * https://docs.jboss.org/hibernate/orm/4.0/hem/en-US/html/listeners.html
        * Jpa 라이프 사이클 : entity에 변화가 일어날 시 callback을 정의 할 수 있다.
            * @PrePersiste
            * @PreUpdate
            * ...
            
