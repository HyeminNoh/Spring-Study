# Java8 Optional

## Optional이란?
Java8부터 Java가 가지고 있는 null의 문제점을 보완하고자 등장하게 된 클래스다.  
Optional<T>는 null이 올 수 있는 값을 감싸는 Wrapper 클래스로 null 값을 참조하더라도 NPE가 발생하지 않도록 도와준다.  
예상치못한 NPE을 복잡한 조건문 없이 제공되는 메소드로 간단히 회피할 수 있다.    
  
## 사용예

1. 전통적인 null 처리 방식
```java
public class BookService {
    public String getAuthorName(Book book) {
        if (book == null) {
            throw new NullPointerException("This book is null");
        }
        
        Author author = book.getAuthor();
        if (author == null) {
            throw new NullPointerException("This author is null");
        }
        return author.getName();
    }
}

// 해당 메서드 호출부
public static void main() {
    BookService bookService = new BookService();
    String authorName = bookService.getAuthorName(book);
    if (authorName == null) {
        throw new NullPointerException("This author name is null");
    }
}
```
null 인지 아닌지 신뢰할 수 없는 부분에 계속해서 NPE을 방어하는 코드를 추가해야 한다.  
간단한 예제에서도 NPE를 막기 위한 로직이 여러 곳에 흩어져 있다.  

2. Optional 사용 예시  
```java
public class BookService {
    // Optional을 사용하여 null에서 안전한 코드 작성하기
    public Optional<String> getAuthorName(Book book) {
        return Optional.ofNullable(book)
                .map(bookObject -> bookObject.getAuthor())
                .map(authorObject -> authorObject.getName());
    }
}
```
if문을 통해 null을 확인하지 않아도 되며 Optional<String> 타입의 자료형을 반환하게 된다.  

위 예시 기준, getAuthorName()을 호출하는 곳에서 결과값으로 null을 받을 수 있게 되므로  
null 참조로 인한 오류를 방지하고 null 결과값이 반환될 경우 인지가 가능하므로  
null이 반환되지 않는 것이 확실한 경우에는 리턴 타입을 String으로 표기하여 호출하는 입장에서 별도의 null 방어 코드를 넣지 않아도 되게 할 수 있다.  
null 방어 코드가 완전히 사라지기 때문에 getAuthorName()이 어떤 로직을 수행하는지 파악이 용이해진다.  

## 메소드 종류  

* 객체를 Optional로 감싸는 메서드
  * of(T):Optional<T>
    * 파라미터로 받은 객체를 Optional로 감싸 반환, 만약 파라미터가 null이면 NPE 발생
  * ofNullable(T):Optional<T>
    * 기본적으로 of()와 동일하나 파라미터가 null이면 빈 Optional을 반환
  * empty():Optional<T>
    * 빈 Optional을 반환, Optional 객체의 중간 연산 중에 값이 null이 되면 내부적으로 이 메서드를 호출
* 중간 연산
  * filter()
  * map()
  * filterMap()
* 종료 연산
  * get()
  * orElse()
  * orelseget()
  * orElseThrow()
* 기타
  * isPresent()
  * ifPresent()


## 장단점
* 장점
  * 코드가 Null-Safe해짐
  * 코드 가독성 향상
  * 애플리케이션 안정성 향상
* 단점
  * NPE 대신 NoSuchElementException 발생
  ```java
  Optional<User> optionalUser = ...;
  //optional이 갖는 value가 없으면 NoSuchElementException 발생
  User user = optionalUser.get();
  ```
  * 코드 가독성 저하 (Optional이 남발된 경우)
    * NoSuchElementException 발생 방지를 위한 예외처리가 또 추가되고 그 아래로 또 NPE를 방지하는 추가 처리가 반복될 수 있다.  
  * 시간적, 공간적 비용 증가
    * Optional은 객체를 감싸는 컨테이너이므로 Optional객체 자체를 저장하기 위한 메모리가 추가로 필요  
    * Optional 안에 있는 객체를 얻기 위해서는 Optional 객체를 통해 젖ㅂ근해야 하므로 접근 비용이 증가  
    * Optional을 사용하면 단순 값 대비 메모리 사용량이 4배 정도 증가한다고도 함  
  * 직렬화 하지 않아 추가적 문제 발생 가능, 이를 해결하기 위한 추가 개발 소요  
    * 캐시, 메시지 큐 등과 연동할 때 문제 발생 가능  
    
* 결론
  * Optional을 목적에 맞게 올바르게 사용하고 남발하지 않도록 해야함
  
* 사용시 유의할 Tip
  * Optional을 파라미터 타입을 감싸는 데 사용 X
    * null인지 아닌지 불명확한 파라미터를 받을 필요가 없기 때문
    * 객체로 받아 메서드 내부에서 Optional로 감싸서 처리하는 편이 메서드를 개발하는 입장이나, 메서드를 사용하는 입장에서도 편리
  * Optional의 값이 비어있는지 확인하는 isPresent() 메서드가 있지만 이를 if문 안에서 확인 X
    * 전통적인 null 처리 방법의 문제를 되풀이하는 꼴이 
  * Optional의 메서드는 크게 Exception을 발생시키는 메서드와 그렇지 않은 메서드로 양분 가능
    * 'Optional을 쓰면 Exception이 발생하지 않는다'는 말은 틀린 말이다. 상황에 맞게 구분하여 사용하자.
  
## Reference & Additional Resources
* <https://esoongan.tistory.com/95>  
* <https://mangkyu.tistory.com/203>
* <https://bbubbush.tistory.com/23>