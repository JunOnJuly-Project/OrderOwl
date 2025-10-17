<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 대시보드</title>
<script src="https://cdn.tailwindcss.com"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="bg-gray-50">
    <!-- 로딩 -->
    <div id="loading" class="hidden fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
        <div class="bg-white rounded-lg p-6 shadow-xl">
            <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500 mx-auto"></div>
            <p class="mt-4 text-gray-700">처리중...</p>
        </div>
    </div>

    <!-- 상단 네비게이션 -->
    <nav class="bg-white border-b border-gray-200">
        <div class="max-w-7xl mx-auto px-4 py-4">
            <h1 class="text-xl font-bold">관리자 대시보드</h1>
        </div>
    </nav>

    <div class="max-w-7xl mx-auto px-4 py-8">
        <div class="flex gap-6">
            <!-- 사이드바 -->
            <aside class="w-64 bg-white rounded-xl p-4 shadow-sm">
                <nav class="space-y-2">
                    <button onclick="showTab('dashboard')" class="tab-btn w-full text-left px-4 py-3 rounded-lg bg-blue-50 text-blue-600 font-medium">
                        대시보드
                    </button>
                    <button onclick="showTab('stores')" class="tab-btn w-full text-left px-4 py-3 rounded-lg text-gray-700 hover:bg-gray-50 font-medium">
                        매장 관리
                    </button>
                    <button onclick="showTab('menus')" class="tab-btn w-full text-left px-4 py-3 rounded-lg text-gray-700 hover:bg-gray-50 font-medium">
                        메뉴 요청 관리
                    </button>
                    <button onclick="showTab('users')" class="tab-btn w-full text-left px-4 py-3 rounded-lg text-gray-700 hover:bg-gray-50 font-medium">
                        유저 관리
                    </button>
                    <button onclick="showTab('sales')" class="tab-btn w-full text-left px-4 py-3 rounded-lg text-gray-700 hover:bg-gray-50 font-medium">
                        매출 정보
                    </button>
                </nav>
            </aside>

            <!-- 메인 컨텐츠 -->
            <main class="flex-1">
                <!-- 대시보드 -->
                <div id="dashboard" class="tab-content">
                    <h2 class="text-2xl font-bold mb-6">대시보드</h2>
                    
                    <!-- 통계 카드 -->
                    <div class="grid grid-cols-4 gap-4 mb-6">
                        <div class="bg-white rounded-xl p-6 shadow-sm">
                            <p class="text-sm text-gray-600">전체 매장</p>
                            <p class="text-2xl font-bold mt-2" id="statStores">0</p>
                        </div>
                        <div class="bg-white rounded-xl p-6 shadow-sm">
                            <p class="text-sm text-gray-600">전체 유저</p>
                            <p class="text-2xl font-bold mt-2" id="statUsers">0</p>
                        </div>
                        <div class="bg-white rounded-xl p-6 shadow-sm">
                            <p class="text-sm text-gray-600">매장 대기 요청</p>
                            <p class="text-2xl font-bold mt-2 text-orange-600" id="statStoreRequests">0</p>
                        </div>
                        <div class="bg-white rounded-xl p-6 shadow-sm">
                            <p class="text-sm text-gray-600">메뉴 대기 요청</p>
                            <p class="text-2xl font-bold mt-2 text-orange-600" id="statMenuRequests">0</p>
                        </div>
                    </div>

                    <!-- ✅ 삭제 대기 매장 섹션 추가 -->
                    <div class="bg-white rounded-xl p-6 shadow-sm mb-6">
                        <h3 class="text-lg font-semibold mb-4 flex items-center gap-2">
                            <span class="bg-red-100 text-red-600 px-3 py-1 rounded-full text-sm font-medium" id="statDeletePending">0</span>
                            삭제 대기 중인 매장
                        </h3>
                        <div id="deletePendingStoreList"></div>
                    </div>

                    <!-- 매장 가입 요청 -->
                    <div class="bg-white rounded-xl p-6 shadow-sm mb-6">
                        <h3 class="text-lg font-semibold mb-4">대기 중인 매장 요청</h3>
                        <div id="storeRequestList"></div>
                    </div>

                    <!-- 메뉴 관련 요청 -->
                    <div class="bg-white rounded-xl p-6 shadow-sm">
                        <h3 class="text-lg font-semibold mb-4">대기 중인 메뉴 요청</h3>
                        <div id="menuRequestList"></div>
                    </div>
                </div>

                <!-- 매장 관리 -->
                <div id="stores" class="tab-content hidden">
                    <h2 class="text-2xl font-bold mb-6">매장 관리</h2>
                    
                    <!-- ✅ 상태별 필터 버튼 추가 -->
                    <div class="bg-white rounded-xl p-4 shadow-sm mb-4">
                        <div class="flex gap-2">
                            <button onclick="filterStores('ALL')" class="filter-btn px-4 py-2 rounded-lg bg-blue-500 text-white text-sm font-medium">
                                전체
                            </button>
                            <button onclick="filterStores('ACTIVE')" class="filter-btn px-4 py-2 rounded-lg bg-gray-100 text-gray-700 text-sm font-medium hover:bg-gray-200">
                                운영중
                            </button>
                            <button onclick="filterStores('PENDING')" class="filter-btn px-4 py-2 rounded-lg bg-gray-100 text-gray-700 text-sm font-medium hover:bg-gray-200">
                                승인대기
                            </button>
                            <button onclick="filterStores('DELETE_PENDING')" class="filter-btn px-4 py-2 rounded-lg bg-gray-100 text-gray-700 text-sm font-medium hover:bg-gray-200">
                                삭제대기
                            </button>
                            <button onclick="filterStores('INACTIVE')" class="filter-btn px-4 py-2 rounded-lg bg-gray-100 text-gray-700 text-sm font-medium hover:bg-gray-200">
                                비활성
                            </button>
                        </div>
                    </div>
                    
                    <div class="bg-white rounded-xl shadow-sm overflow-hidden">
                        <table class="w-full">
                            <thead class="bg-gray-50">
                                <tr>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">매장명</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">업주 ID</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">메뉴 수</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">총 주문</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">총 매출</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">상태</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">액션</th>
                                </tr>
                            </thead>
                            <tbody id="storeTable"></tbody>
                        </table>
                    </div>
                </div>

                <!-- 메뉴 요청 관리 -->
                <div id="menus" class="tab-content hidden">
                    <h2 class="text-2xl font-bold mb-6">메뉴 요청 관리</h2>
                    
                    <div class="bg-white rounded-xl p-6 shadow-sm mb-6">
                        <h3 class="text-lg font-semibold mb-4">메뉴 추가 요청</h3>
                        <div id="menuAddRequests"></div>
                    </div>

                    <div class="bg-white rounded-xl p-6 shadow-sm mb-6">
                        <h3 class="text-lg font-semibold mb-4">메뉴 수정 요청</h3>
                        <div id="menuUpdateRequests"></div>
                    </div>

                    <div class="bg-white rounded-xl p-6 shadow-sm">
                        <h3 class="text-lg font-semibold mb-4">메뉴 삭제 요청</h3>
                        <div id="menuDeleteRequests"></div>
                    </div>
                </div>

                <!-- 유저 관리 -->
                <div id="users" class="tab-content hidden">
                    <h2 class="text-2xl font-bold mb-6">유저 관리</h2>
                    <div class="bg-white rounded-xl shadow-sm overflow-hidden">
                        <table class="w-full">
                            <thead class="bg-gray-50">
                                <tr>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">이름</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">이메일</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">주문 수</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">총 결제액</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">상태</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">액션</th>
                                </tr>
                            </thead>
                            <tbody id="userTable"></tbody>
                        </table>
                    </div>
                </div>

                <!-- 매출 정보 -->
                <div id="sales" class="tab-content hidden">
                    <h2 class="text-2xl font-bold mb-6">매출 정보</h2>
                    
                    <div class="bg-white rounded-xl p-6 shadow-sm mb-6">
                        <label class="block text-sm font-medium text-gray-700 mb-2">매장 선택</label>
                        <select id="salesStoreSelect" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
                            <option value="">매장을 선택하세요</option>
                        </select>
                    </div>

                    <div class="bg-white rounded-xl p-6 shadow-sm mb-6">
                        <div class="grid grid-cols-2 gap-4">
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-2">시작일</label>
                                <input type="date" id="startDate" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
                            </div>
                            <div>
                                <label class="block text-sm font-medium text-gray-700 mb-2">종료일</label>
                                <input type="date" id="endDate" class="w-full px-4 py-2 border border-gray-300 rounded-lg">
                            </div>
                        </div>
                        <button onclick="loadStoreSales()" class="mt-4 px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600">
                            조회하기
                        </button>
                    </div>

                    <div id="salesStats" class="hidden">
                        <div class="grid grid-cols-3 gap-4 mb-6">
                            <div class="bg-white rounded-xl p-6 shadow-sm">
                                <p class="text-sm text-gray-600 mb-2">총 매출</p>
                                <p class="text-3xl font-bold" id="totalSales">₩0</p>
                            </div>
                            <div class="bg-white rounded-xl p-6 shadow-sm">
                                <p class="text-sm text-gray-600 mb-2">총 주문 수</p>
                                <p class="text-3xl font-bold" id="totalOrders">0</p>
                            </div>
                            <div class="bg-white rounded-xl p-6 shadow-sm">
                                <p class="text-sm text-gray-600 mb-2">평균 주문 금액</p>
                                <p class="text-3xl font-bold" id="avgOrderAmount">₩0</p>
                            </div>
                        </div>

                        <div class="bg-white rounded-xl p-6 shadow-sm mb-6">
                            <h3 class="text-lg font-semibold mb-4">일별 매출</h3>
                            <div id="dailySalesList"></div>
                        </div>

                        <div class="bg-white rounded-xl p-6 shadow-sm">
                            <h3 class="text-lg font-semibold mb-4">메뉴별 매출</h3>
                            <div id="menuSalesList"></div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <!-- 매장 상세 모달 -->
    <div id="storeModal" class="hidden fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="bg-white rounded-xl p-6 max-w-2xl w-full mx-4 max-h-[90vh] overflow-y-auto">
            <h3 class="text-xl font-bold mb-4" id="modalStoreTitle"></h3>
            <div class="space-y-4">
                <div class="grid grid-cols-2 gap-4">
                    <div>
                        <p class="text-sm text-gray-600">업주 ID</p>
                        <p class="font-medium" id="modalStoreOwner"></p>
                    </div>
                    <div>
                        <p class="text-sm text-gray-600">사업자 번호</p>
                        <p class="font-medium" id="modalBusinessNumber"></p>
                    </div>
                    <div>
                        <p class="text-sm text-gray-600">주소</p>
                        <p class="font-medium" id="modalAddress"></p>
                    </div>
                    <div>
                        <p class="text-sm text-gray-600">전화번호</p>
                        <p class="font-medium" id="modalPhone"></p>
                    </div>
                    <div>
                        <p class="text-sm text-gray-600">메뉴 수</p>
                        <p class="font-medium" id="modalMenuCount"></p>
                    </div>
                    <div>
                        <p class="text-sm text-gray-600">총 주문</p>
                        <p class="font-medium" id="modalTotalOrders"></p>
                    </div>
                    <div class="col-span-2">
                        <p class="text-sm text-gray-600">총 매출</p>
                        <p class="font-medium text-lg" id="modalStoreSales"></p>
                    </div>
                </div>
                <div class="flex gap-3 mt-6">
                    <button onclick="viewStoreSalesModal()" class="flex-1 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600">
                        매출 상세 보기
                    </button>
                    <button onclick="createQRModal()" class="flex-1 px-4 py-2 bg-purple-500 text-white rounded-lg hover:bg-purple-600">
                        QR 코드 생성
                    </button>
                    <button onclick="deleteStoreModal()" class="flex-1 px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600">
                        매장 삭제
                    </button>
                    <button onclick="closeModal()" class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50">
                        닫기
                    </button>
                </div>
            </div>
        </div>
    </div>

<script>
let currentStoreId = null;
let stores = [];
let users = [];
let currentFilter = 'ALL'; // ✅ 현재 필터 상태

$(document).ready(function() {
    console.log('✅ 페이지 로드 완료');
    loadData();
    initDatePickers();
});

function initDatePickers() {
    let today = new Date().toISOString().split('T')[0];
    let monthAgo = new Date(Date.now() - 30 * 24 * 60 * 60 * 1000).toISOString().split('T')[0];
    $('#startDate').val(monthAgo);
    $('#endDate').val(today);
}

function showTab(tab) {
    $('.tab-content').addClass('hidden');
    $('#' + tab).removeClass('hidden');
    
    $('.tab-btn').removeClass('bg-blue-50 text-blue-600').addClass('text-gray-700');
    event.target.classList.remove('text-gray-700');
    event.target.classList.add('bg-blue-50', 'text-blue-600');
    
    if (tab === 'sales') {
        loadStoresForSales();
    }
}

// ✅ 매장 필터 기능 추가
function filterStores(status) {
    currentFilter = status;
    
    // 필터 버튼 스타일 업데이트
    $('.filter-btn').removeClass('bg-blue-500 text-white').addClass('bg-gray-100 text-gray-700');
    event.target.classList.remove('bg-gray-100', 'text-gray-700');
    event.target.classList.add('bg-blue-500', 'text-white');
    
    // 필터링된 매장 표시
    displayFilteredStores();
}

// ✅ 필터링된 매장 표시
function displayFilteredStores() {
    let filteredStores = stores;
    
    if (currentFilter !== 'ALL') {
        filteredStores = stores.filter(s => s.status === currentFilter);
    }
    
    let html = '';
    if (filteredStores.length === 0) {
        html = '<tr><td colspan="7" class="px-6 py-8 text-center text-gray-500">해당 상태의 매장이 없습니다</td></tr>';
    } else {
        filteredStores.forEach(function(store) {
            html += '<tr class="border-t hover:bg-gray-50">';
            html += '<td class="px-6 py-4">' + (store.storeName || '-') + '</td>';
            html += '<td class="px-6 py-4">' + (store.ownerId || '-') + '</td>';
            html += '<td class="px-6 py-4">' + (store.menuCount || 0) + '</td>';
            html += '<td class="px-6 py-4">' + (store.totalOrders || 0) + '</td>';
            html += '<td class="px-6 py-4">₩' + (store.totalSales || 0).toLocaleString() + '</td>';
            html += '<td class="px-6 py-4"><span class="px-2 py-1 rounded-full text-xs font-medium ' + getStatusClass(store.status) + '">' + getStatusText(store.status) + '</span></td>';
            html += '<td class="px-6 py-4"><button onclick="viewStoreDetail(' + store.storeId + ')" class="px-3 py-1.5 bg-blue-500 text-white rounded-lg text-sm hover:bg-blue-600">상세</button></td>';
            html += '</tr>';
        });
    }
    $('#storeTable').html(html);
    
    console.log('✅ 필터 적용: ' + currentFilter + ' (' + filteredStores.length + '개 매장)');
}

function callAPI(methodName, params, callback) {
    console.log('🔵 API 호출:', methodName, params);
    $('#loading').removeClass('hidden');
    
    $.ajax({
        url: '${pageContext.request.contextPath}/front',
        method: 'GET',
        data: $.extend({key: 'admin', methodName: methodName}, params),
        success: function(response) {
            $('#loading').addClass('hidden');
            console.log('✅ API 성공:', methodName, response);
            if (callback) callback(response);
        },
        error: function(xhr, status, error) {
            $('#loading').addClass('hidden');
            console.error('❌ API 에러:', methodName);
            console.error('Status:', status);
            console.error('Error:', error);
            console.error('Response:', xhr.responseText);
            alert('서버 오류가 발생했습니다: ' + error + '\n자세한 내용은 콘솔을 확인하세요.');
        }
    });
}

function loadData() {
    console.log('📊 데이터 로딩 시작...');
    loadStoreRequests();
    loadMenuRequests();
    loadStores();
    loadUsers();
    loadDeletePendingStores(); // ✅ 삭제 대기 매장 로드
}

// ✅ 삭제 대기 매장 로드 함수
function loadDeletePendingStores() {
    callAPI('getDeletePendingStores', {}, function(res) {
        if (res && res.success) {
            let stores = res.data || [];
            $('#statDeletePending').text(stores.length);
            
            let html = '';
            if (stores.length === 0) {
                html = '<p class="text-gray-500 text-center py-4">삭제 대기 중인 매장이 없습니다</p>';
            } else {
                stores.forEach(function(store) {
                    html += '<div class="flex items-center justify-between p-4 bg-red-50 border border-red-200 rounded-lg mb-3">';
                    html += '<div>';
                    html += '<p class="font-medium text-red-900">' + (store.storeName || '-') + '</p>';
                    html += '<p class="text-sm text-red-700">사업자번호: ' + (store.businessNumber || '-') + '</p>';
                    html += '<p class="text-sm text-red-700">전화번호: ' + (store.phoneNumber || '-') + '</p>';
                    html += '</div>';
                    html += '<div class="flex gap-2">';
                    html += '<button onclick="approveStoreDeletion(' + store.storeId + ')" class="px-3 py-1.5 bg-red-600 text-white rounded-lg text-sm hover:bg-red-700">최종 삭제</button>';
                    html += '<button onclick="cancelStoreDeletion(' + store.storeId + ')" class="px-3 py-1.5 bg-gray-500 text-white rounded-lg text-sm hover:bg-gray-600">취소</button>';
                    html += '</div>';
                    html += '</div>';
                });
            }
            $('#deletePendingStoreList').html(html);
        }
    });
}

// ✅ 삭제 최종 승인
function approveStoreDeletion(storeId) {
    if (!confirm('정말 이 매장을 완전히 삭제하시겠습니까?\n이 작업은 되돌릴 수 없습니다.')) return;
    
    callAPI('approveStoreDeletion', {storeId: storeId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            loadDeletePendingStores();
            loadStores();
            updateDashboardStats();
        }
    });
}

// ✅ 삭제 취소
function cancelStoreDeletion(storeId) {
    if (!confirm('매장 삭제를 취소하고 다시 활성화하시겠습니까?')) return;
    
    callAPI('cancelStoreDeletion', {storeId: storeId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            loadDeletePendingStores();
            loadStores();
            updateDashboardStats();
        }
    });
}

function loadStoreRequests() {
    callAPI('getStoreRequests', {}, function(res) {
        let requests = res && res.data ? res.data : [];
        $('#statStoreRequests').text(requests.length);
        
        let html = '';
        if (requests.length === 0) {
            html = '<p class="text-gray-500 text-center py-4">대기 중인 요청이 없습니다</p>';
        } else {
            requests.forEach(function(req) {
                html += '<div class="flex items-center justify-between p-4 bg-gray-50 rounded-lg mb-3">';
                html += '<div>';
                html += '<p class="font-medium">' + (req.storeName || '-') + '</p>';
                html += '<p class="text-sm text-gray-600">사업자번호: ' + (req.businessNumber || '-') + '</p>';
                html += '<p class="text-sm text-gray-600">전화번호: ' + (req.phoneNumber || '-') + '</p>';
                html += '</div>';
                html += '<div class="flex gap-2">';
                html += '<button onclick="approveStoreRequest(' + req.requestId + ', \'ADD\')" class="px-3 py-1.5 bg-green-500 text-white rounded-lg text-sm hover:bg-green-600">승인</button>';
                html += '<button onclick="rejectStoreRequest(' + req.requestId + ')" class="px-3 py-1.5 bg-red-500 text-white rounded-lg text-sm hover:bg-red-600">거절</button>';
                html += '</div>';
                html += '</div>';
            });
        }
        $('#storeRequestList').html(html);
        
        console.log('✅ 매장 요청 목록 갱신 완료: ' + requests.length + '개 요청');
    });
}

function loadMenuRequests() {
    callAPI('getMenuRequests', {}, function(res) {
        let requests = res && res.data ? res.data : [];
        $('#statMenuRequests').text(requests.length);
        
        let html = '<p class="text-gray-500 text-center py-4">대기 중인 요청이 없습니다</p>';
        $('#menuRequestList').html(html);
    });
}

function loadStores() {
    callAPI('getStoreList', {}, function(res) {
        if (res && res.success) {
            stores = res.data ? res.data : [];
            
            // ✅ PENDING 상태가 아닌 매장만 카운트 (통계용)
            let activeStoresCount = stores.filter(s => s.status !== 'PENDING').length;
            $('#statStores').text(activeStoresCount);
            
            // ✅ 필터 적용하여 표시
            displayFilteredStores();
            
            console.log('✅ 매장 목록 갱신 완료: ' + stores.length + '개 매장 (활성: ' + activeStoresCount + '개)');
        } else {
            console.error('❌ 매장 목록 로드 실패:', res);
        }
    });
}

function getStatusClass(status) {
    switch(status) {
        case 'ACTIVE': return 'bg-green-100 text-green-800';
        case 'PENDING': return 'bg-yellow-100 text-yellow-800';
        case 'DELETE_PENDING': return 'bg-red-100 text-red-800';
        case 'INACTIVE': return 'bg-gray-100 text-gray-800';
        default: return 'bg-gray-100 text-gray-800';
    }
}

function getStatusText(status) {
    switch(status) {
        case 'ACTIVE': return '운영중';
        case 'PENDING': return '대기중';
        case 'DELETE_PENDING': return '삭제대기';
        case 'INACTIVE': return '비활성';
        default: return status;
    }
}

function loadUsers() {
    callAPI('getUserList', {}, function(res) {
        users = res && res.data ? res.data : [];
        $('#statUsers').text(users.length);
        
        let html = '<tr><td colspan="6" class="px-6 py-8 text-center text-gray-500">등록된 유저가 없습니다</td></tr>';
        $('#userTable').html(html);
    });
}

function approveStoreRequest(requestId, requestType) {
    if (!confirm('매장 요청을 승인하시겠습니까?')) return;
    
    let methodName = 'approveStoreInfoAddRequest';
    callAPI(methodName, {requestId: requestId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            console.log('✅ 매장 요청 승인 성공, 데이터 갱신 시작...');
            closePendingModal(); // ✅ 모달 닫기
            loadStoreRequests();
            loadStores();
            updateDashboardStats();
            updateSalesStoreSelect();
        }
    });
}

function updateDashboardStats() {
    $('#statStores').text(stores.length);
    $('#statUsers').text(users.length);
}

function rejectStoreRequest(requestId) {
    if (!confirm('매장 요청을 거절하시겠습니까?')) return;
    alert('거절 기능은 구현 중입니다.');
}

function viewStoreDetail(storeId) {
    // ✅ PENDING 상태 매장 (음수 ID)인 경우 승인/거절 모달 표시
    if (storeId < 0) {
        showPendingStoreModal(-storeId); // 음수를 양수로 변환 (requestId)
        return;
    }
    
    // 일반 매장 상세 정보
    callAPI('getStoreInfo', {storeId: storeId}, function(res) {
        if (res && res.success && res.data) {
            currentStoreId = storeId;
            let store = res.data;
            $('#modalStoreTitle').text((store.storeName || '매장') + ' 상세 정보');
            $('#modalStoreOwner').text(store.ownerId || '-');
            $('#modalBusinessNumber').text(store.businessNumber || '-');
            $('#modalAddress').text(store.address || '-');
            $('#modalPhone').text(store.phoneNumber || '-');
            $('#modalMenuCount').text((store.menuCount || 0) + '개');
            $('#modalTotalOrders').text((store.totalOrders || 0) + '건');
            $('#modalStoreSales').text('₩' + (store.totalSales || 0).toLocaleString());
            $('#storeModal').removeClass('hidden');
        } else {
            alert('매장 정보를 불러올 수 없습니다.');
        }
    });
}

// ✅ PENDING 매장 승인/거절 모달
function showPendingStoreModal(requestId) {
    // 매장 요청 정보 찾기
    let store = stores.find(s => s.storeId === -requestId);
    if (!store) {
        alert('매장 요청 정보를 찾을 수 없습니다.');
        return;
    }
    
    let modalHtml = `
        <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50" id="pendingStoreModal">
            <div class="bg-white rounded-xl p-6 max-w-2xl w-full mx-4">
                <h3 class="text-xl font-bold mb-4">매장 등록 요청 - ${store.storeName}</h3>
                <div class="space-y-4">
                    <div class="grid grid-cols-2 gap-4">
                        <div>
                            <p class="text-sm text-gray-600">업주 ID</p>
                            <p class="font-medium">${store.ownerId || '-'}</p>
                        </div>
                        <div>
                            <p class="text-sm text-gray-600">사업자 번호</p>
                            <p class="font-medium">${store.businessNumber || '-'}</p>
                        </div>
                        <div>
                            <p class="text-sm text-gray-600">주소</p>
                            <p class="font-medium">${store.address || '-'}</p>
                        </div>
                        <div>
                            <p class="text-sm text-gray-600">전화번호</p>
                            <p class="font-medium">${store.phoneNumber || '-'}</p>
                        </div>
                    </div>
                    <div class="flex gap-3 mt-6">
                        <button onclick="approveStoreRequest(${requestId}, 'ADD')" class="flex-1 px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600">
                            승인
                        </button>
                        <button onclick="rejectStoreRequest(${requestId})" class="flex-1 px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600">
                            거절
                        </button>
                        <button onclick="closePendingModal()" class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50">
                            닫기
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `;
    
    $('body').append(modalHtml);
}

function closePendingModal() {
    $('#pendingStoreModal').remove();
}

function createQRModal() {
    if (!currentStoreId) return;
    callAPI('createStoreQR', {storeId: currentStoreId}, function(res) {
        alert(res.message || 'QR 코드가 생성되었습니다.');
    });
}

// ✅ 매장 상세 모달에서 삭제 요청 (DELETE_PENDING 상태로 변경)
function deleteStoreModal() {
    if (!currentStoreId) return;
    if (!confirm('이 매장의 삭제를 요청하시겠습니까?\n\n진행 중인 주문이 있는 경우 삭제 요청을 할 수 없습니다.\n삭제 요청 후 대시보드의 "삭제 대기 중인 매장"에서 최종 승인이 필요합니다.')) return;
    
    callAPI('deleteStore', {storeId: currentStoreId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            closeModal();
            loadStores();
            loadDeletePendingStores(); // 삭제 대기 목록 갱신
            updateDashboardStats();
        }
    });
}

function viewStoreSalesModal() {
    if (!currentStoreId) return;
    closeModal();
    showTab('sales');
    $('#salesStoreSelect').val(currentStoreId);
    loadStoreSales();
}

function closeModal() {
    $('#storeModal').addClass('hidden');
    currentStoreId = null;
}

function loadStoresForSales() {
    if (stores.length > 0) {
        updateSalesStoreSelect();
    } else {
        loadStores();
        setTimeout(updateSalesStoreSelect, 500);
    }
}

function updateSalesStoreSelect() {
    let html = '<option value="">매장을 선택하세요</option>';
    stores.forEach(function(store) {
        html += '<option value="' + store.storeId + '">' + (store.storeName || '매장') + '</option>';
    });
    $('#salesStoreSelect').html(html);
}

function loadStoreSales() {
    let storeId = $('#salesStoreSelect').val();
    if (!storeId) {
        alert('매장을 선택해주세요.');
        return;
    }
    
    let startDate = $('#startDate').val();
    let endDate = $('#endDate').val();
    
    if (!startDate || !endDate) {
        alert('기간을 선택해주세요.');
        return;
    }
    
    callAPI('getStoreSalesInfo', {
        storeId: storeId,
        startDate: startDate,
        endDate: endDate
    }, function(res) {
        if (res && res.success && res.data) {
            let report = res.data;
            $('#totalSales').text('₩' + (report.totalSales || 0).toLocaleString());
            $('#totalOrders').text((report.totalOrders || 0) + '건');
            $('#avgOrderAmount').text('₩' + (report.averageOrderAmount || 0).toLocaleString());
            $('#salesStats').removeClass('hidden');
            
            let dailyHtml = '';
            if (report.dailySales && report.dailySales.length > 0) {
                report.dailySales.forEach(function(daily) {
                    dailyHtml += '<div class="flex justify-between items-center p-3 border-b">';
                    dailyHtml += '<span>' + daily.saleDate + '</span>';
                    dailyHtml += '<span class="font-semibold">₩' + (daily.dailyTotal || 0).toLocaleString() + '</span>';
                    dailyHtml += '</div>';
                });
            } else {
                dailyHtml = '<p class="text-gray-500 text-center py-4">일별 매출 데이터가 없습니다</p>';
            }
            $('#dailySalesList').html(dailyHtml);
            
            let menuHtml = '';
            if (report.menuSales && report.menuSales.length > 0) {
                report.menuSales.forEach(function(menu) {
                    menuHtml += '<div class="flex justify-between items-center p-3 border-b">';
                    menuHtml += '<div>';
                    menuHtml += '<p class="font-medium">' + menu.menuName + '</p>';
                    menuHtml += '<p class="text-sm text-gray-600">판매량: ' + (menu.totalQuantity || 0) + '개</p>';
                    menuHtml += '</div>';
                    menuHtml += '<span class="font-semibold">₩' + (menu.totalSales || 0).toLocaleString() + '</span>';
                    menuHtml += '</div>';
                });
            } else {
                menuHtml = '<p class="text-gray-500 text-center py-4">메뉴별 매출 데이터가 없습니다</p>';
            }
            $('#menuSalesList').html(menuHtml);
        } else {
            alert('매출 정보를 불러올 수 없습니다.');
        }
    });
}

function approveMenuAddRequest(requestId) {
    if (!confirm('메뉴 추가 요청을 승인하시겠습니까?')) return;
    
    callAPI('approveMenuAddRequest', {requestId: requestId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            loadMenuRequests();
        }
    });
}

function approveMenuUpdateRequest(requestId) {
    if (!confirm('메뉴 수정 요청을 승인하시겠습니까?')) return;
    
    callAPI('approveMenuUpdateRequest', {requestId: requestId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            loadMenuRequests();
        }
    });
}

function approveMenuDeleteRequest(requestId) {
    if (!confirm('메뉴 삭제 요청을 승인하시겠습니까?')) return;
    
    callAPI('approveMenuDeleteRequest', {requestId: requestId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            loadMenuRequests();
        }
    });
}

function forceDeleteUser(userId) {
    let reason = prompt('강제 탈퇴 사유를 입력해주세요:');
    if (!reason || reason.trim() === '') {
        alert('탈퇴 사유를 입력해주세요.');
        return;
    }
    
    if (!confirm('정말 이 유저를 강제 탈퇴 처리하시겠습니까?')) return;
    
    callAPI('forceDeleteUser', {userId: userId, reason: reason}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            loadUsers();
        }
    });
}
</script>
</body>
</html>