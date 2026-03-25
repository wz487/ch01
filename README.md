# 1. Conceptualization

**Student No:** 22413244  
**Name:** 왕진  
**E-mail:** 2205570641@qq.com

---

## Revision history

| Revision date | Version # | Description | Author |
|---------------|-----------|-------------|--------|
| 03/20/2026    | 1.00      | 기본 UI 구축 | 왕진   |

---

## Table of Contents

1. Business purpose
2. System context diagram
3. Use case list
4. Concept of operation
5. Problem statement
6. Glossary
7. References

---

## 1. Business purpose

### (1) 프로젝트 배경

본 프로젝트는 Java Swing 기반의 쇼핑 시스템으로, 객체 지향 프로그램망 개념, 파일 기반 데이터 지속성, GUI 애플리케이션 개발을 시연하는 것을 목표로 합니다. 온라인 쇼핑 플랫폼을 시물레이션하며, 고객은 상품을 조회하고, 장바구니를 관리하고, 주문을 하고, 주문 내역을 확인할 수 있습니다. 반면 관리자는 상품 재고와 사용자 계정을 관리할 수 있습니다.

### (2) 프로젝트 동기

고객과 관리자라는 서로 다른 역할을 가진 다중 사용자 시스템의 실제 사례를 만들고, 사용자 인증, 장바구니 관리, 주문 처리를 포함한 전자상거래 운영의 전체 프로세스를 보여주고자 합니다.

### (3) 대상 시장

교육 환경(예: 소프트웨어 공학 수업), 간단한 데스크톱 POS 시스템이 필요한 소규모 소매 기업, 또는 Java GUI 및 객체 지향 디자인을 학습하는 개발자.

---

## 2. System context diagram

### 역할 설명

- **고객**: 등록된 사용자로, 상품을 조회하고, 장바구니를 관리하며, 주문하고, 주문 내역을 확인할 수 있습니다.
- **관리자**: 특별 권한을 가진 사전 정의된 사용자로, 상품 카탈로그를 관리하고 사용자 계정을 삭제할 수 있습니다.

---

## 3. Use case list

### 1) Register

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 사용자명과 비밀번호를 입력하여 새 계정을 생성한다. |

### 2) Login

| 항목 | 내용 |
|------|------|
| Actor | Customer, Administrator |
| Description | 사용자(고객 또는 관리자)의 신원을 확인하여 시스템에 접근할 수 있도록 한다. |

### 3) View Product List

| 항목 | 내용 |
|------|------|
| Actor | Customer, Administrator |
| Description | 모든 사용 가능한 상품의 ID, 이름, 가격을 표시한다. |

### 4) Add to Cart

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 선택한 상품과 수량을 장바구니에 추가한다. |

### 5) View Cart

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 현재 장바구니에 담긴 모든 상품, 수량 및 총 금액을 표시한다. |

### 6) Increase Cart Item Quantity

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 장바구니에 있는 지정 상품의 수량을 1 증가시킨다. |

### 7) Decrease Cart Item Quantity

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 장바구니에 있는 지정 상품의 수량을 1 감소시킨다 (수량이 0이 되면 제거한다). |

### 8) Remove Cart Item

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 장바구니에서 지정한 상품을 삭제한다. |

### 9) Clear Cart

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 장바구니에 있는 모든 상품을 삭제한다. |

### 10) Confirm Customer Info

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 주문을 생성하기 전에 개인 정보(이름, 전화번호, 주소)를 입력하거나 수정한다. |

### 11) Place Order

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 현재 장바구니 내용을 주문으로 변환하여 주문 내역에 저장하고 장바구니를 비운다. |

### 12) View Order History

| 항목 | 내용 |
|------|------|
| Actor | Customer |
| Description | 모든 과거 주문의 상세 정보를 표시한다. |

### 13) Manage Products

| 항목 | 내용 |
|------|------|
| Actor | Administrator |
| Description | 상품 목록에서 상품을 추가, 수정 또는 삭제한다. |

### 14) Manage Users

| 항목 | 내용 |
|------|------|
| Actor | Administrator |
| Description | 사용자 계정을 삭제한다 (현재 로그인한 관리자 또는 자기 자신은 삭제할 수 없다). |

---

## 4. Concept of operation

### 1) 로그인

| 항목 | 내용 |
|------|------|
| Purpose | 사용자 신원을 확인하여 시스템에 접근할 수 있도록 한다. |
| Approach | 사용자가 사용자명과 비밀번호를 입력하면 시스템은 저장된 데이터와 비교하여 검증한다. |
| Dynamics | 시스템은 입력된 자격 증명이 등록된 사용자 또는 하드코딩된 관리자 계정과 일치하는지 확인한다. |
| Goals | 안전한 접근 제어를 구현한다. |

### 2) 장바구니에 추가

| 항목 | 내용 |
|------|------|
| Purpose | 고객이 상품을 구매 예정 목록에 담을 수 있도록 한다. |
| Approach | 고객이 상품과 수량을 선택하면 시스템은 이를 장바구니에 추가한다(이미 존재하는 경우 수량을 증가시킨다). |
| Dynamics | 추가 작업 직후 장바구니 화면이 즉시 갱신된다. |
| Goals | 장바구니 구성 기능을 구현한다. |

### 3) 주문하기

| 항목 | 내용 |
|------|------|
| Purpose | 구매 프로세스를 완료하고 주문을 기록한다. |
| Approach | 고객이 개인 정보를 확인하면 시스템은 현재 장바구니 내용을 기반으로 주문을 생성하고, 저장한 후 장바구니를 비운다. |
| Dynamics | 확인 대화상자에 주문 상세 내역을 표시한다. |
| Goals | 트랜잭션을 완료하고 주문을 지속성(persistence) 저장한다. |

### 4) 상품 관리

| 항목 | 내용 |
|------|------|
| Purpose | 관리자가 상품 카탈로그를 업데이트할 수 있도록 한다. |
| Approach | 관리자는 목록에서 상품을 선택하고, 버튼을 통해 추가·삭제·수정 작업을 수행한다. |
| Dynamics | 변경 사항은 상품 목록에 즉시 반영된다. |
| Goals | 최신 재고 정보를 유지한다. |

### 5) 주문 내역 조회

| 항목 | 내용 |
|------|------|
| Purpose | 고객에게 과거 구매 기록을 제공한다. |
| Approach | 고객이 매뉴 항목을 선택하면 시스템은 저장된 모든 주문을 검색하여 표시한다. |
| Dynamics | 포맷이 적용된 대화상자에 주문 목록을 보여준다. |
| Goals | 고객이 과거 구매 내역을 확인할 수 있도록 한다. |

---

## 5. Problem statement

### 프로그램 고려 사항 (기술적 난제 포함)

- **데이터 지속성 (Persistence)**: 시스템은 Java 직렬화(users.dat)에 의존한다. 이 방식은 간단하지만, 동시 접근이나 대규모 데이터에는 적합하지 않으며, 파일 손상이나 버전 비호환 문제가 발생할 수 있다.
- **동시성 (Concurrency)**: 애플리케이션은 단일 사용자 환경을 기준으로 설계되었으며, 멀티스레딩이나 데이터베이스 잠금이 구현되어 있지 않아 여러 사용자가 동시에 사용하기에는 적합하지 않다.
- **GUI 복잡성**: 여러 개의 중첩된 프레임과 다이얼로그를 사용하여 인터페이스를 구성하였으며, 창의 포커스와 단기 동작을 관리하는 데 복잡성이 발생할 수 있다.
- **보안 (Security)**: 비밀번호가 암호화나 해싱 없이 평문(plain text)으로 저장되어 있어 보안 위험이 존재한다.
- **하드코딩된 관리자 계정**: 관리자 계정 정보("wz521" / "666666")가 코드에 직접 작성되어 있어 수정이 불가능하다.

### 비기능적 요구사항 (Non-functional Requirements)

- **사용성 (Usability)**: GUI는 직관적이어야 하며, 명확한 라벨과 피드백 메시지를 제공해야 한다.
- **신뢰성 (Reliability)**: 모든 데이터 변경 사항은 일관되게 저장되어 데이터 손실을 방지해야 한다.
- **성능 (Performance)**: 일반적인 하드웨어 환경에서 모든 작업의 응답 시간은 1초 미만이어야 한다.
- **유지보수성 (Maintainability)**: 코드는 모듈화되어야 하며, 데이터 모델, 관리자, UI 코드 간의 관심사 분리가 명확해야 한다.
- **이식성 (Portability)**: 애플리케이션은 Java 8 이상을 지원하는 모든 플랫폼에서 실행 가능해야 한다.

---

## 6. Glossary

| 용어 | 설명 |
|------|------|
| 고객 | 쇼핑 권한을 가진 등록된 사용자 |
| 관리자 | 상품과 사용자를 관리할 수 있는 특별 권한을 가진 사전 정의된 사용자 |

---

## 7. References

1. Oracle. (2023). *Java Swing 教程*. Retrieved from https://docs.oracle.com/javase/tutorial/uiswing/
2. Bloch, J. (2018). *Effective Java* (第3版). Addison-Wesley.
3. Java 对象序列化规范. (2023). Retrieved from https://docs.oracle.com/javase/8/docs/platform/serialization/spec/serialTOC.html
