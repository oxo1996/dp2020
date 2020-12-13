Design Patterns 2020 텀프로젝트:
Holub EmbeddedSQL 기능 확장 및 설계 개선


프로젝트 개요:

1. Holub EmbeddedSQL 프로젝트에 대해 설계패턴을 적용하여 기능을 확장하고 설계를 개선
2. 수업 시간에 다룬 설계패턴 및 객체지향 설계 개념을 활용하며, 그 결과를 문서화
3. 기존 EmbeddedSQL 프로젝트가 지원하던 기능은 그대로 동작
4. 기능 확장과 설계 개선 활동이 잘 되었다는 것을 JUnit을 이용해 테스트
5. MUST 확장 기능의 4번(visitor 패턴 적용)은 구현하지 못함.

MUST 확장 기능:

(1) HTML Exporter 개발:
빌더 패턴이 적용된 기존 exporter 클래스를 참고하여 HTML exporter를 개발

(2) XML Importer 와 XML Exporter. XML:
위와 유사하게 XML 태그를 이용한 importer와 exporter를 개발

(3) Console (com.holub.database.jdbc 패키지 밑의 Console.java)에서 다음의 쿼리를 수
행하면 에러가 나는데, 소스코드를 수정하여 해당 쿼리가 잘 수행되도록 기능 확장.
select * from address, name where address.addrId = name.addrId
참고로, 프로그램에서 조인 기능 자체는 잘 동작함. 다음 쿼리는 에러를 발생하지 않음
select street from address, name where address.addrId = name.addrId
