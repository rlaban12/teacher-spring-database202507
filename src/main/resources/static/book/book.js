// 백엔드 API 서버의 기본 URL
const URL = '/api/v1/books';

//=========== 렌더링 관련 함수 ============//

// 책 목록을 화면에 렌더링하는 함수
const renderBooks = (bookList) => {
  // 1. 책 목록을 담을 ul 태그와 총 권수를 표시할 태그를 가져옵니다.
  const $bookList = document.querySelector('.book-list');
  const $bookCount = document.querySelector('.book-count');

  // 2. 렌더링을 시작하기 전에 기존 목록을 전부 비웁니다.
  //    이 과정이 없으면 목록을 새로고침할 때마다 책들이 중복되어 나타납니다.
  $bookList.innerHTML = '';

  // 3. 총 권수를 업데이트합니다.
  $bookCount.textContent = bookList.length;

  // 4. 받아온 책 목록(bookList)을 순회하면서 li 태그를 생성합니다.
  bookList.forEach(book => {
    const $newLi = document.createElement('li');
    // 생성된 li 태그에 data-id 속성으로 책의 고유 ID를 부여합니다. (삭제 시 사용)
    $newLi.dataset.id = book.id;

    $newLi.innerHTML = `
      <div class="info">
          <h3>${book.title}</h3>
          <p>${book.author}</p>
      </div>
      <div class="price">
          <span>${book.isbn}</span>
          <button class="del-btn">삭제</button>
      </div>
    `;
    $bookList.append($newLi);
  });
};


//=========== 서버 데이터 요청/응답 관련 함수 ============//

// 서버에서 책 목록을 가져오는 비동기 함수
const fetchGetBooks = async (sort = 'id') => {
  // 정렬 파라미터를 URL에 추가합니다. 기본값은 'id'입니다.
  const res = await fetch(`${URL}?sort=${sort}`);
  const result = await res.json();
  // 받아온 데이터로 렌더링 함수를 호출합니다.
  renderBooks(result);
};

// 서버에 책을 등록하는 비동기 함수
const fetchPostBook = async () => {
  // 1. 폼에서 사용자가 입력한 제목, 저자, isbn 정보를 가져옵니다.
  const $titleInput = document.getElementById('title');
  const $authorInput = document.getElementById('author');
  const $isbnInput = document.getElementById('isbn');

  // 2. 입력값이 모두 채워졌는지 간단히 확인합니다.
  if (!$titleInput.value.trim() || !$authorInput.value.trim() || !$isbnInput.value) {
    alert('모든 필드를 채워주세요!');
    return;
  }

  // 3. 서버에 보낼 데이터를 객체로 만듭니다.
  const payload = {
    title: $titleInput.value,
    author: $authorInput.value,
    isbn: $isbnInput.value,
  };

  // 4. fetch를 사용하여 POST 요청을 보냅니다.
  await fetch(`${URL}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });

  // 5. 등록이 완료된 후, 입력창을 비우고 목록을 새로고침합니다.
  $titleInput.value = '';
  $authorInput.value = '';
  $isbnInput.value = '';

  await fetchGetBooks(); // 목록 새로고침
};


// 서버에 책 삭제를 요청하는 비동기 함수
const fetchDeleteBook = async (id) => {
  // 정말 삭제할 것인지 사용자에게 한 번 더 확인합니다.
  if (!confirm(`${id}번 도서를 정말로 삭제하시겠습니까?`)) {
    return;
  }

  // fetch를 사용하여 DELETE 요청을 보냅니다.
  const res = await fetch(`${URL}/${id}`, {
    method: 'DELETE',
  });

  await fetchGetBooks(); // 목록 새로고침
};


//=========== 이벤트 핸들러 설정 ============//

// 각종 이벤트(클릭, 제출 등)를 감지하고 그에 맞는 함수를 호출하는 핸들러
const addEventListeners = () => {
  const $bookForm = document.getElementById('book-form');
  const $sortButtons = document.querySelector('.sort-buttons');
  const $bookList = document.querySelector('.book-list');

  // 1. 도서 등록 폼 제출(submit) 이벤트
  $bookForm.addEventListener('submit', (e) => {
    // 폼 제출 시 브라우저가 새로고침되는 기본 동작을 막습니다.
    e.preventDefault();
    fetchPostBook();
  });

  // 2. 정렬 버튼 클릭 이벤트 (이벤트 위임 사용)
  $sortButtons.addEventListener('click', (e) => {
    // 클릭된 요소가 .sort-btn 클래스를 가지고 있는지 확인
    if (!e.target.matches('.sort-btn')) {
      return;
    }
    // 클릭된 버튼의 data-sort 속성 값을 가져와 fetch 함수에 전달
    fetchGetBooks(e.target.dataset.sort);
  });

  // 3. 삭제 버튼 클릭 이벤트 (이벤트 위임 사용)
  $bookList.addEventListener('click', (e) => {
    if (!e.target.matches('.del-btn')) {
      return;
    }
    // 클릭된 삭제 버튼에서 가장 가까운 li 태그를 찾아 data-id 값을 가져옵니다.
    const bookId = e.target.closest('li').dataset.id;
    fetchDeleteBook(bookId);
  });
};


//=========== 메인 코드 실행 ============//
(function () {
  // 페이지가 처음 열렸을 때 기본 목록(id순)을 불러옵니다.
  fetchGetBooks();

  // 이벤트 핸들러들을 등록합니다.
  addEventListeners();
})();