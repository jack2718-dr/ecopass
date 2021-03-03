/**
 * 이 파일은 apsol-ecopass 프로젝트의 전반에 필요한 유틸을 담아둔다
 */


/**
 * 빈 값을 검사한다
 * 넘어온 값이 "Blank"이면 "true"를 반환한다
 * https://github.com/SangHakLee/is 참고
 * @param val
 * @returns {boolean}
 */
function isBlank (val) {
    if (val === null) return true;
    if (typeof val === 'undefined') return true;
    if (typeof val === 'string' && val === '') return true;
    if (typeof val === 'string' && val === ' ') return true;
    if (Array.isArray(val) && val.length < 1) return true;
    if (typeof val === 'object' && val.constructor.name === 'Object' && Object.keys(val).length < 1 && Object.getOwnPropertyNames(val) < 1) return true;
    if (typeof val === 'object' && val.constructor.name === 'String' && Object.keys(val).length < 1) return true;

    return false;
}

/**
 * <input>의 keyup 이벤트에 맞추어
 * <input>값을 숫자만 남기고 반환하며
 * 전화번호 양식에 맞게 "-" 특수문자를 넣어준다
 * @param id    *jquery selector text
 */
function onKeyupAddHyphen(id) {
    $(id).on("keyup", function() {
        $(id).val(
            addHyphen( $(id).val() )
        );
    });
}

/**
 * 전달 받은 값에서 숫자만 남기고 반환하며
 * 전화번호 양식에 맞게 "-" 특수문자를 넣어준다
 * @param val
 * @returns {*}
 */
function addHyphen(val) {
    var temp = allowOnlyNumbers(val)
       .replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3")
       .replace("--", "-")
    return temp;
}

/**
 * <input>의 keyup 이벤트에 맞추어
 * allowOnlyNumbers() 메서드를 실행시킨다
 * 결과적으로 숫자만 입력받도록 강제한다
 * @param id    *jquery selector text
 */
function onKeyupAllowOnlyNumbers(id){
    $(id).on("keyup", function() {
        $(id).val(allowOnlyNumbers($(id).val()));
    })
}

/**
 * 전달 받은 값에서 숫자만 남기고 반환한다
 * @param val    *String
 */
function allowOnlyNumbers(val) {
    return val.replace(/[^0-9]/g, "")
}

/**
 * <input>에 "jquery-ui 의 datePicker"를 등록한다
 * @param id    *jquery selector text
 */
function setDatePicker(id) {
    $(id).datepicker({
        dateFormat 			: 'yy.mm.dd',
        prevText			: '이전 달',
        nextText			: '다음 달',
        monthNames			: [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
        monthNamesShort		: [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
        dayNames			: [ '일', '월', '화', '수', '목', '금', '토' ],
        dayNamesShort		: [ '일', '월', '화', '수', '목', '금', '토' ],
        dayNamesMin			: [ '일', '월', '화', '수', '목', '금', '토' ],
        yearSuffix			: '년',
        setDate				: new Date(),
        showMonthAfterYear	: true,
    });
}

/**
 * <input> "datePicker"를 과거일로 설정할 수 없도록 강제한다
 * @param id    *jquery selector text
 */
function blockPastDate(id) {
    $(id).on("change", function() { 				    // 이벤트 감지시
        var selectDate = parseDotDateToObject($(id).val());

        if ( selectDate.getTime() < new Date().getTime() ) {        // 오늘 날짜와 비교
            alert("배출예약일을 과거로 설정할 수 없습니다.");
            $(id).val(getDotDate(getFutureDate(0)));           // 초기날짜 (당일)로 되돌리기
        }
    });
}

/**
 * "yyyy.mm.dd" 문자열을 "Date Object"로 변환한다
 * @param dotDate       *"yyyy.mm.dd"
 * @returns {Date}      *Date object
 */
function parseDotDateToObject(dotDate) {
    var date = new Date(dotDate.substring(0, 4) * 1,		// 년
        dotDate.substring(5, 7) * 1 - 1,	// 월
        dotDate.substring(8) 	* 1		);	// 일
    return date;
}

/**
 * "Date Object"를 "yyyy.mm.dd" 문자열로 바꾸어 반환한다.
 * @param date          *Date Object
 * @returns {string}    *"yyyy.mm.dd"
 */
function getDotDate(date){
    var dotDate = date.getFullYear() + '.'
        + String('0' + (date.getMonth() + 1)).slice(-2) + '.'
        + String('0' + date.getDate()).slice(-2);
    return dotDate;
}

/**
 *
 * @param days
 * @returns {Date}
 */
function getFutureDate(days) {
    var date = new Date();
    date.setDate(date.getDate() + days);
    return date;
}
















































