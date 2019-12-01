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
     
         
         
            
