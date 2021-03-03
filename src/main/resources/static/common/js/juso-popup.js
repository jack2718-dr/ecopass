/**
 * 해당 JS 파일을 주입하는 페이지는
 * "JusoDto"를 상속받는 "DTO"의 "<form>"을 구현해야 합니다.
 *
 * 주소 팝업을 호출하는 "openJusoPopup()"메소드를 이용합니다.
 *
 * 호출되는 팝업 페이지는 "/templates/common/juso-popup.html"입니다.
 */


/**
 * 팝업을 호출한다.
 */
function openJusoPopup() {
    window.open("/common/juso-popup/page", "pop",
    "width=570,height=420, scrollbars=yes, resizable=yes");
}

/**
 * 팝업 검색결과를 돌려받는다.
 * @param JusoResponseDto
 */
function callbackJuso(JusoResponseDto) {
    // 기존 데이터 삭제
    emptyJuso();
    // 재할당
    $("#addrJibun").val(JusoResponseDto.addrJibun);
    $("#addrRoad").val(JusoResponseDto.addrRoad);
    $("#addrDetail").val(JusoResponseDto.addrDetail.replace(/ /g, ""));
    $("#sido").val(JusoResponseDto.sido);
    $("#sgg").val(JusoResponseDto.sgg);
    $("#zipCode").val(JusoResponseDto.zipCode);
    $("#bemd").val(JusoResponseDto.bemd);
    $("#lat").val(JusoResponseDto.lat);
    $("#lng").val(JusoResponseDto.lng);

    handleHemds(JusoResponseDto.hemdList);
}

/**
 * "<form>"에 저장되어 있는 주소 데이터를 삭제한다
 */
function emptyJuso(){
    $("#addrJibun").val('');
    $("#addrRoad").val('');
    $("#addrDetail").val('');
    $("#sido").val('');
    $("#sgg").val('');
    $("#zipCode").val('');
    $("#bemd").val('');
    $("#lat").val('');
    $("#lng").val('');

    $("#hemd").empty();
    $("#hemd").append("<option value=\" \">필수 선택</option>");
}

/**
 * 행정동 개수에 따라 동작을 분기한다
 * @param hemdList
 */
function handleHemds(hemdList) {
    if (hemdList.length === 0) {
        emptyJuso();
        alert("INVALID\n주소검색 :: 선택하신 주소는 관할 지역이 아닙니다.\n다시 시도해주세요.");
    } else if (hemdList.length === 1) {
        confirmHemd(hemdList);
    } else if (hemdList.length >= 2) {
        listupHemds(hemdList);
    }
}

/**
 * "행정동 법정동 매칭 테이블의 쿼리 결과"에 단 한개 행정동만 존재하는 경우
 * @param hemdList
 */
function confirmHemd(hemdList) {
    var hemd = hemdList[0];
    $("#hemd").empty(); // "필수 선택" 옵션 삭제 후 1개 행정동 주입
    listupHemdSelectTag(hemd);
}

/**
 * "행정동 법정동 매칭 테이블의 쿼리 결과"에 여러개 행정동이 존재하는 경우
 * @param hemdList
 */
function listupHemds(hemdList) {
    hemdList.forEach(function(hemd) {
        listupHemdSelectTag(hemd);
    })
}

/**
 * 행정동 <select> 태그의 <option>을 세팅한다
 * @param hemd
 */
function listupHemdSelectTag(hemd){
    $("#hemd").append("<option th:value=\"" + hemd + "\">" + hemd + "</option>");
}

/**
 * 주소 관련 <input>을 검사한다
 * @returns {boolean}
 */
function validJusoInputs() {
    if (isBlank($("#addrJibun").val())) {
        alert("INVALID\n주소를 검색해주세요");
        return false;
    }
    if (isBlank($("#addrRoad").val())) {
        alert("INVALID\n주소를 검색해주세요");
        return false;
    }
    if (isBlank($("#addrDetail").val())) {
        alert("INVALID\n상세주소를 입력해주세요");
        return false;
    }
    if (isBlank($("#zipCode").val())) {
        alert("INVALID\n주소를 검색해주세요");
        return false;
    }
    if (isBlank($("#sido").val())) {
        alert("INVALID\n주소를 검색해주세요");
        return false;
    }
    if (isBlank($("#sgg").val())) {
        alert("INVALID\n주소를 검색해주세요");
        return false;
    }
    if (isBlank($("#bemd").val())) {
        alert("INVALID\n주소를 검색해주세요");
        return false;
    }
    if (isBlank($("#hemd option:selected").val())) {
        alert("INVALID\n행정동을 선택해주세요");
        return false;
    }
    if (isBlank($("#lat").val())) {
        alert("INVALID\n주소를 검색해주세요");
        return false;
    }
    if (isBlank($("#lng").val())) {
        alert("INVALID\n주소를 검색해주세요");
        return false;
    }
    return true;
}
