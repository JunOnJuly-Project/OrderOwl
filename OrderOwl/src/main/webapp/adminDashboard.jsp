<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<div id="loading"
		class="hidden fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
		<div class="bg-white rounded-lg p-6 shadow-xl">
			<div
				class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500 mx-auto"></div>
			<p class="mt-4 text-gray-700">처리중...</p>
		</div>
	</div>

	<!-- 상단 네비게이션 -->
	<nav class="bg-white border-b border-gray-200">
		<div class="max-w-7xl mx-auto px-4 py-4">
			<h1 class="text-xl font-bold">🦉 OrderOwl 관리자 대시보드</h1>
		</div>
	</nav>

	<div class="max-w-7xl mx-auto px-4 py-8">
		<div class="flex gap-6">
			<!-- 사이드바 -->
			<aside class="w-64 bg-white rounded-xl p-4 shadow-sm">
				<nav class="space-y-2">
					<button onclick="showTab('dashboard')"
						class="tab-btn w-full text-left px-4 py-3 rounded-lg bg-blue-50 text-blue-600 font-medium">
						📊 대시보드</button>
					<button onclick="showTab('stores')"
						class="tab-btn w-full text-left px-4 py-3 rounded-lg text-gray-700 hover:bg-gray-50 font-medium">
						🏪 매장 관리</button>
					<button onclick="showTab('menus')"
						class="tab-btn w-full text-left px-4 py-3 rounded-lg text-gray-700 hover:bg-gray-50 font-medium">
						🍽️ 메뉴 관리</button>
					<button onclick="showTab('qr')"
						class="tab-btn w-full text-left px-4 py-3 rounded-lg text-gray-700 hover:bg-gray-50 font-medium">
						📱 QR 관리</button>
					<button onclick="showTab('sales')"
						class="tab-btn w-full text-left px-4 py-3 rounded-lg text-gray-700 hover:bg-gray-50 font-medium">
						💰 매출 정보</button>
				</nav>
			</aside>

			<!-- 메인 컨텐츠 -->
			<main class="flex-1">
				<!-- 대시보드 -->
				<div id="dashboard" class="tab-content">
					<h2 class="text-2xl font-bold mb-6">대시보드</h2>

					<!-- 통계 카드 -->
					<div class="grid grid-cols-2 gap-4 mb-6">
						<div class="bg-white rounded-xl p-6 shadow-sm">
							<p class="text-sm text-gray-600">전체 매장</p>
							<p class="text-2xl font-bold mt-2" id="statStores">0</p>
						</div>
						<div class="bg-white rounded-xl p-6 shadow-sm">
							<p class="text-sm text-gray-600">전체 유저</p>
							<p class="text-2xl font-bold mt-2" id="statUsers">0</p>
						</div>
					</div>

					<!-- 최근 등록 매장 -->
					<div class="bg-white rounded-xl p-6 shadow-sm">
						<h3 class="text-lg font-semibold mb-4">최근 등록된 매장</h3>
						<div id="recentStoreList">
							<p class="text-gray-500 text-center py-8">매장 정보를 불러오는 중...</p>
						</div>
					</div>
				</div>

				<!-- 매장 관리 -->
				<div id="stores" class="tab-content hidden">
					<h2 class="text-2xl font-bold mb-6">매장 관리</h2>

					<div class="bg-white rounded-xl shadow-sm overflow-hidden">
						<table class="w-full">
							<thead class="bg-gray-50">
								<tr>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">매장명</th>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">업주
										ID</th>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">지역</th>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">전화번호</th>
									<th
										class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">액션</th>
								</tr>
							</thead>
							<tbody id="storeTable"></tbody>
						</table>
					</div>
				</div>

				<!-- 메뉴 관리 -->
				<div id="menus" class="tab-content hidden">
					<h2 class="text-2xl font-bold mb-6">메뉴 관리</h2>

					<!-- 매장 선택 -->
					<div class="bg-white rounded-xl p-6 shadow-sm mb-6">
						<label class="block text-sm font-medium text-gray-700 mb-2">매장
							선택</label> <select id="menuStoreSelect" onchange="loadStoreMenus()"
							class="w-full px-4 py-2 border border-gray-300 rounded-lg">
							<option value="">매장을 선택하세요</option>
						</select>
					</div>

					<!-- 메뉴 목록 -->
					<div id="menuListSection" class="hidden">
						<div class="bg-white rounded-xl p-6 shadow-sm">
							<div class="flex justify-between items-center mb-4">
								<h3 class="text-lg font-semibold">메뉴 목록</h3>
							</div>

							<div class="overflow-x-auto">
								<table class="w-full">
									<thead class="bg-gray-50">
										<tr>
											<th
												class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">메뉴명</th>
											<th
												class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">가격</th>
											<th
												class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">설명</th>
											<th
												class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">액션</th>
										</tr>
									</thead>
									<tbody id="menuTableBody"></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>

				<!-- QR 관리 -->
				<div id="qr" class="tab-content hidden">
					<h2 class="text-2xl font-bold mb-6">QR 코드 관리</h2>

					<div class="bg-white rounded-xl p-6 shadow-sm mb-6">
						<div class="flex items-center gap-2 mb-4">
							<svg class="w-6 h-6 text-blue-500" fill="none"
								stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round"
									stroke-linejoin="round" stroke-width="2"
									d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                            </svg>
							<h3 class="text-lg font-semibold">QR 코드 안내</h3>
						</div>
						<div class="text-sm text-gray-600 space-y-2">
							<p>• 각 매장마다 고유한 QR 코드가 생성됩니다.</p>
							<p>• 고객이 QR 코드를 스캔하면 해당 매장의 주문 페이지로 이동합니다.</p>
							<p>
								• QR 코드 URL:
								<code class="bg-gray-100 px-2 py-1 rounded">https://yourapp.com/order?store=매장ID</code>
							</p>
							<p>• QR 코드 이미지를 다운로드하여 매장에 비치하세요.</p>
						</div>
					</div>

					<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
						id="qrCodeList">
						<!-- QR 코드 카드들이 여기에 동적으로 생성됩니다 -->
					</div>
				</div>

				<!-- 매출 정보 -->
				<div id="sales" class="tab-content hidden">
					<h2 class="text-2xl font-bold mb-6">매출 정보</h2>

					<div class="bg-white rounded-xl p-6 shadow-sm mb-6">
						<label class="block text-sm font-medium text-gray-700 mb-2">매장
							선택</label> <select id="salesStoreSelect"
							class="w-full px-4 py-2 border border-gray-300 rounded-lg">
							<option value="">매장을 선택하세요</option>
						</select>
					</div>

					<div class="bg-white rounded-xl p-6 shadow-sm mb-6">
						<div class="grid grid-cols-2 gap-4">
							<div>
								<label class="block text-sm font-medium text-gray-700 mb-2">시작일</label>
								<input type="date" id="startDate"
									class="w-full px-4 py-2 border border-gray-300 rounded-lg">
							</div>
							<div>
								<label class="block text-sm font-medium text-gray-700 mb-2">종료일</label>
								<input type="date" id="endDate"
									class="w-full px-4 py-2 border border-gray-300 rounded-lg">
							</div>
						</div>
						<button onclick="loadStoreSales()"
							class="mt-4 px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600">
							조회하기</button>
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

	<!-- 매장 수정 모달 -->
	<div id="storeModal" class="hidden fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
		<div class="bg-white rounded-xl p-6 max-w-lg w-full mx-4">
			<h3 class="text-xl font-bold mb-4" id="storeModalTitle">매장 수정</h3>
			<form id="storeForm">
				<input type="hidden" id="storeId" name="storeId">
				<input type="hidden" id="ownerId" name="ownerId">
				
				<div class="space-y-4">
					<div>
						<label class="block text-sm font-medium text-gray-700 mb-2">매장명 *</label>
						<input type="text" id="storeName" name="storeName" required
							class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
					</div>
					
					<div>
						<label class="block text-sm font-medium text-gray-700 mb-2">주소</label>
						<input type="text" id="address" name="address"
							class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
					</div>
					
					<div>
						<label class="block text-sm font-medium text-gray-700 mb-2">지역</label>
						<input type="text" id="region" name="region"
							class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
					</div>
					
					<div>
						<label class="block text-sm font-medium text-gray-700 mb-2">전화번호</label>
						<input type="text" id="phoneNumber" name="phoneNumber"
							class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
					</div>
					
					<div>
						<label class="block text-sm font-medium text-gray-700 mb-2">설명</label>
						<textarea id="description" name="description" rows="3"
							class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"></textarea>
					</div>
					
					<div>
						<label class="block text-sm font-medium text-gray-700 mb-2">이미지 URL</label>
						<input type="text" id="imgSrc" name="imgSrc"
							class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
					</div>
				</div>
				
				<div class="flex gap-3 mt-6">
					<button type="submit"
						class="flex-1 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600">
						저장
					</button>
					<button type="button" onclick="closeStoreModal()"
						class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50">
						취소
					</button>
				</div>
			</form>
		</div>
	</div>

	<!-- 메뉴 추가/수정 모달 -->
	<div id="menuModal"
		class="hidden fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
		<div class="bg-white rounded-xl p-6 max-w-lg w-full mx-4">
			<h3 class="text-xl font-bold mb-4" id="menuModalTitle">메뉴 수정</h3>
			<form id="menuForm">
				<input type="hidden" id="menuId" name="menuId"> 
				<input type="hidden" id="menuStoreId" name="storeId">

				<div class="space-y-4">
					<div>
						<label class="block text-sm font-medium text-gray-700 mb-2">메뉴명 *</label> 
						<input type="text" id="menuName" name="menuName" required
							class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
					</div>

					<div>
						<label class="block text-sm font-medium text-gray-700 mb-2">가격 (원) *</label> 
						<input type="number" id="menuPrice" name="price" required min="0"
							class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
					</div>

					<div>
						<label class="block text-sm font-medium text-gray-700 mb-2">설명</label>
						<textarea id="menuDescription" name="description" rows="3"
							class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"></textarea>
					</div>
				</div>

				<div class="flex gap-3 mt-6">
					<button type="submit"
						class="flex-1 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600">
						저장</button>
					<button type="button" onclick="closeMenuModal()"
						class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50">
						취소</button>
				</div>
			</form>
		</div>
	</div>

	<script>
// ==================== 전역 변수 ====================
let currentMenuStoreId = null;
let isEditMode = false;

// ==================== 초기화 ====================
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

// ==================== 탭 전환 ====================
function showTab(tab) {
    $('.tab-content').addClass('hidden');
    $('#' + tab).removeClass('hidden');
    
    $('.tab-btn').removeClass('bg-blue-50 text-blue-600').addClass('text-gray-700');
    event.target.classList.remove('text-gray-700');
    event.target.classList.add('bg-blue-50', 'text-blue-600');
    
    // 탭 전환 시 데이터 로드
    if (tab === 'stores') {
        loadStoreList();
    } else if (tab === 'qr') {
        loadQRCodes();
    } else if (tab === 'menus') {
        loadStoreListForMenus();
    } else if (tab === 'sales') {
        loadStoreListForSales();
    }
}

// ==================== API 호출 ====================
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
            alert('서버 오류가 발생했습니다: ' + error);
        }
    });
}

// ==================== 데이터 로딩 ====================
function loadData() {
    console.log('📊 데이터 로딩 시작...');
    loadDashboardStats();
}

// ==================== 대시보드 ====================
function loadDashboardStats() {
    // 매장 수 조회
    callAPI('getStoreList', {}, function(res) {
        if (res && res.success) {
            $('#statStores').text(res.data.length);
            
            // 최근 매장 표시
            let html = '';
            if (res.data.length === 0) {
                html = '<p class="text-gray-500 text-center py-8">등록된 매장이 없습니다</p>';
            } else {
                let recentStores = res.data.slice(0, 5);
                html = '<div class="space-y-2">';
                recentStores.forEach(function(store) {
                    html += '<div class="flex justify-between items-center py-3 border-b">';
                    html += '<div>';
                    html += '<p class="font-medium">' + store.storeName + '</p>';
                    html += '<p class="text-sm text-gray-500">' + (store.region || '') + '</p>';
                    html += '</div>';
                    html += '<span class="text-sm text-gray-500">ID: ' + store.storeId + '</span>';
                    html += '</div>';
                });
                html += '</div>';
            }
            $('#recentStoreList').html(html);
        }
    });
    
    // 유저 수 조회
    callAPI('getUserList', {}, function(res) {
        if (res && res.success) {
            $('#statUsers').text(res.data.length);
        }
    });
}

// ==================== 매장 관리 ====================
function loadStoreList() {
    callAPI('getStoreList', {}, function(res) {
        if (res && res.success) {
            let stores = res.data || [];
            
            let html = '';
            if (stores.length === 0) {
                html = '<tr><td colspan="5" class="px-6 py-8 text-center text-gray-500">등록된 매장이 없습니다</td></tr>';
            } else {
                stores.forEach(function(store) {
                    html += '<tr class="border-b hover:bg-gray-50">';
                    html += '<td class="px-6 py-4 font-medium">' + store.storeName + '</td>';
                    html += '<td class="px-6 py-4">' + store.ownerId + '</td>';
                    html += '<td class="px-6 py-4">' + (store.region || '-') + '</td>';
                    html += '<td class="px-6 py-4">' + (store.phoneNumber || '-') + '</td>';
                    html += '<td class="px-6 py-4">';
                    html += '<div class="flex gap-2">';
                    html += '<button onclick="editStore(' + store.storeId + ')" class="px-3 py-1.5 bg-blue-500 text-white rounded-lg text-sm hover:bg-blue-600">수정</button>';
                    html += '<button onclick="deleteStore(' + store.storeId + ')" class="px-3 py-1.5 bg-red-500 text-white rounded-lg text-sm hover:bg-red-600">삭제</button>';
                    html += '</div>';
                    html += '</td>';
                    html += '</tr>';
                });
            }
            $('#storeTable').html(html);
            
            console.log('✅ 매장 목록 갱신 완료: ' + stores.length + '개 매장');
        }
    });
}

function editStore(storeId) {
    callAPI('getStoreForEdit', {storeId: storeId}, function(res) {
        if (res && res.success) {
            let store = res.data;
            
            $('#storeModalTitle').text('매장 수정');
            $('#storeId').val(store.storeId);
            $('#ownerId').val(store.ownerId);
            $('#storeName').val(store.storeName || '');
            $('#address').val(store.address || '');
            $('#region').val(store.region || '');
            $('#phoneNumber').val(store.phoneNumber || '');
            $('#description').val(store.description || '');
            $('#imgSrc').val(store.imgSrc || '');
            
            $('#storeModal').removeClass('hidden');
        } else {
            alert('매장 정보를 불러오는데 실패했습니다.');
        }
    });
}

function closeStoreModal() {
    $('#storeModal').addClass('hidden');
    $('#storeForm')[0].reset();
}

// 매장 폼 제출 처리
$('#storeForm').on('submit', function(e) {
    e.preventDefault();
    
    let formData = {
        storeId: $('#storeId').val(),
        ownerId: $('#ownerId').val(),
        storeName: $('#storeName').val(),
        address: $('#address').val(),
        region: $('#region').val(),
        phoneNumber: $('#phoneNumber').val(),
        description: $('#description').val(),
        imgSrc: $('#imgSrc').val()
    };
    
    if (!confirm('매장 정보를 수정하시겠습니까?')) return;
    
    callAPI('updateStoreInfo', formData, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            closeStoreModal();
            loadStoreList();
            loadDashboardStats();
        }
    });
});

function deleteStore(storeId) {
    if (!confirm('이 매장을 삭제하시겠습니까?\n\n진행 중인 주문이 있는 매장은 삭제할 수 없습니다.')) return;
    
    callAPI('deleteStore', {storeId: storeId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            loadStoreList();
            loadDashboardStats();
        }
    });
}

//==================== 메뉴 관리 ====================
function loadStoreListForMenus() {
    callAPI('getStoreList', {}, function(res) {
        if (res && res.success) {
            let html = '<option value="">매장을 선택하세요</option>';
            res.data.forEach(function(store) {
                html += '<option value="' + store.storeId + '">' + store.storeName + '</option>';
            });
            $('#menuStoreSelect').html(html);
        }
    });
}

function loadStoreMenus() {
    let storeId = $('#menuStoreSelect').val();
    
    if (!storeId) {
        $('#menuListSection').addClass('hidden');
        return;
    }
    
    currentMenuStoreId = storeId;
    
    callAPI('getStoreMenus', {storeId: storeId}, function(res) {
        if (res && res.success) {
            let menus = res.data || [];
            
            let html = '';
            if (menus.length === 0) {
                html = '<tr><td colspan="4" class="px-6 py-8 text-center text-gray-500">등록된 메뉴가 없습니다</td></tr>';
            } else {
                menus.forEach(function(menu) {
                    html += '<tr class="border-t hover:bg-gray-50">';
                    html += '<td class="px-6 py-4 font-medium">' + (menu.menuName || '-') + '</td>';
                    html += '<td class="px-6 py-4">₩' + (menu.price || 0).toLocaleString() + '</td>';
                    html += '<td class="px-6 py-4 text-sm text-gray-600">' + (menu.description || '-') + '</td>';
                    html += '<td class="px-6 py-4">';
                    html += '<div class="flex gap-2">';
                    html += '<button onclick="editMenu(' + menu.menuId + ')" class="px-3 py-1.5 bg-blue-500 text-white rounded-lg text-sm hover:bg-blue-600">수정</button>';
                    html += '<button onclick="deleteMenu(' + menu.menuId + ')" class="px-3 py-1.5 bg-red-500 text-white rounded-lg text-sm hover:bg-red-600">삭제</button>';
                    html += '</div>';
                    html += '</td>';
                    html += '</tr>';
                });
            }
            
            $('#menuTableBody').html(html);
            $('#menuListSection').removeClass('hidden');
        }
    });
}

function editMenu(menuId) {
    callAPI('getStoreMenus', {storeId: currentMenuStoreId}, function(res) {
        if (res && res.success) {
            let menu = res.data.find(m => m.menuId === menuId);
            if (menu) {
                isEditMode = true;
                $('#menuModalTitle').text('메뉴 수정');
                $('#menuId').val(menu.menuId);
                $('#menuStoreId').val(menu.storeId);
                $('#menuName').val(menu.menuName);
                $('#menuPrice').val(menu.price);
                $('#menuDescription').val(menu.description);
                $('#menuModal').removeClass('hidden');
            }
        }
    });
}

function deleteMenu(menuId) {
    if (!confirm('이 메뉴를 삭제하시겠습니까?\n\n진행 중인 주문에 포함된 메뉴는 삭제할 수 없습니다.')) return;
    
    callAPI('deleteMenuDirect', {menuId: menuId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            loadStoreMenus();
        }
    });
}

function closeMenuModal() {
    $('#menuModal').addClass('hidden');
    $('#menuForm')[0].reset();
}

$('#menuForm').on('submit', function(e) {
    e.preventDefault();
    
    let formData = {
        storeId: $('#menuStoreId').val(),
        menuName: $('#menuName').val(),
        price: $('#menuPrice').val(),
        description: $('#menuDescription').val()
    };
    
    // 수정 모드만 남김
    formData.menuId = $('#menuId').val();
    
    if (!confirm('메뉴를 수정하시겠습니까?')) return;
    
    callAPI('updateMenuDirect', formData, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            closeMenuModal();
            loadStoreMenus();
        }
    });
});

//==================== 테이블별 QR 관리 ====================

function loadQRCodes() {
    callAPI('getStoreList', {}, function(res) {
        if (res && res.success) {
            let stores = res.data || [];
            console.log('✅ QR 로드된 매장:', stores);
            
            let html = '';
            if (stores.length === 0) {
                html = '<div class="col-span-3 text-center py-12 text-gray-500">등록된 매장이 없습니다</div>';
            } else {
                // 매장 선택 및 안내문
                html += '<div class="col-span-3 bg-white rounded-xl p-6 shadow-sm mb-6">';
                html += '<div class="flex items-center gap-2 mb-4">';
                html += '<svg class="w-6 h-6 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">';
                html += '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>';
                html += '</svg>';
                html += '<h3 class="text-lg font-semibold">테이블별 QR 코드 안내</h3>';
                html += '</div>';
                html += '<div class="text-sm text-gray-600 space-y-2">';
                html += '<p>• 각 테이블마다 고유한 QR 코드가 생성됩니다.</p>';
                html += '<p>• 고객이 QR 코드를 스캔하면 해당 테이블의 주문 페이지로 이동합니다.</p>';
                html += '<p>• QR 코드 URL: <code class="bg-gray-100 px-2 py-1 rounded">https://yourapp.com/order?store=매장ID&table=테이블ID</code></p>';
                html += '<p>• QR 코드 이미지를 다운로드하여 각 테이블에 비치하세요.</p>';
                html += '</div>';
                html += '</div>';
                
                // 매장 선택
                html += '<div class="col-span-3 bg-white rounded-xl p-6 shadow-sm mb-6">';
                html += '<label class="block text-sm font-medium text-gray-700 mb-2">매장 선택</label>';
                html += '<select id="qrStoreSelect" onchange="loadStoreTables()" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">';
                html += '<option value="">매장을 선택하세요</option>';
                stores.forEach(function(store) {
                    html += '<option value="' + store.storeId + '">' + store.storeName + ' (ID: ' + store.storeId + ')</option>';
                });
                html += '</select>';
                html += '</div>';
                
                // 테이블 QR 목록 영역
                html += '<div id="tableQRList" class="col-span-3">';
                html += '<div class="text-center py-12 text-gray-500">매장을 선택하면 테이블 목록이 표시됩니다</div>';
                html += '</div>';
            }
            
            $('#qrCodeList').html(html);
        }
    });
}

function loadStoreTables() {
    let storeId = $('#qrStoreSelect').val();
    
    if (!storeId) {
        $('#tableQRList').html('<div class="text-center py-12 text-gray-500">매장을 선택하면 테이블 목록이 표시됩니다</div>');
        return;
    }
    
    callAPI('getStoreTables', {storeId: storeId}, function(res) {
        if (res && res.success) {
            let tables = res.data || [];
            console.log('✅ 테이블 목록:', tables);
            
            let html = '';
            if (tables.length === 0) {
                html = '<div class="text-center py-12 text-gray-500">등록된 테이블이 없습니다</div>';
            } else {
                html += '<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">';
                
                tables.forEach(function(table) {
                    // QR 코드 데이터 설정
                    let hasQR = table.qrcodeData && table.qrImgSrc;
                    let qrPath = hasQR ? table.qrcodeData : 
                                 ('https://yourapp.com/order?store=' + table.storeId + '&table=' + table.tableId);
                    
                    // URL 인코딩 함수 (재귀 문제 해결)
                    let encodedQRPath = encodeURIComponentSafe(qrPath);
                    let qrImageUrl = hasQR ? table.qrImgSrc : 
                                     ('https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=' + encodedQRPath);
                    
                    html += '<div class="bg-white rounded-xl p-6 shadow-sm border">';
                    html += '<div class="flex flex-col items-center">';
                    
                    // QR 코드 이미지
                    html += '<div class="w-48 h-48 bg-gray-100 rounded-lg mb-4 flex items-center justify-center overflow-hidden border-2 ' + (hasQR ? 'border-green-200' : 'border-red-200') + '">';
                    html += '<img src="' + qrImageUrl + '" alt="QR Code" class="w-full h-full object-contain" ';
                    html += 'onerror="this.src=\'https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=' + encodedQRPath + '\'">';
                    html += '</div>';
                    
                    // 테이블 정보
                    html += '<h3 class="text-lg font-semibold mb-1 text-center">' + (table.storeName || '매장') + '</h3>';
                    html += '<p class="text-sm text-gray-600 mb-1">테이블: <span class="font-medium">' + (table.tableNo || '-') + '</span></p>';
                    html += '<p class="text-xs text-gray-500 mb-2">테이블 ID: ' + table.tableId + '</p>';
                    
                    // QR 상태 표시
                    if (hasQR) {
                        html += '<span class="px-2 py-1 bg-green-100 text-green-800 text-xs rounded-full mb-3">✓ QR 생성됨</span>';
                    } else {
                        html += '<span class="px-2 py-1 bg-red-100 text-red-800 text-xs rounded-full mb-3">⚠ QR 미생성</span>';
                    }
                    
                    // QR 경로 표시
                    html += '<div class="w-full mb-4">';
                    html += '<div class="text-xs text-gray-500 text-center break-all px-2 py-1 bg-gray-50 rounded border" title="' + qrPath + '">';
                    html += qrPath.length > 40 ? qrPath.substring(0, 40) + '...' : qrPath;
                    html += '</div>';
                    html += '</div>';
                    
                    // 액션 버튼
                    html += '<div class="flex flex-col gap-2 w-full">';
                    if (!hasQR) {
                        html += '<button onclick="generateTableQR(' + table.tableId + ')" class="w-full px-4 py-2 bg-green-500 text-white rounded-lg text-sm hover:bg-green-600 transition-colors">';
                        html += '🔄 QR 생성하기';
                        html += '</button>';
                    }
                    html += '<button onclick="downloadTableQR(' + table.tableId + ', \'' + escapeQuotes(table.storeName) + '\', \'' + escapeQuotes(table.tableNo) + '\', \'' + escapeQuotes(qrPath) + '\')" ';
                    html += 'class="w-full px-4 py-2 bg-blue-500 text-white rounded-lg text-sm hover:bg-blue-600 transition-colors ' + (hasQR ? '' : 'opacity-50 cursor-not-allowed') + '" ';
                    html += (hasQR ? '' : 'disabled') + '>';
                    html += '💾 QR 다운로드';
                    html += '</button>';
                    html += '</div>';
                    
                    html += '</div>';
                    html += '</div>';
                });
                
                html += '</div>';
            }
            
            $('#tableQRList').html(html);
        }
    });
}

function generateTableQR(tableId) {
    if (!confirm('이 테이블의 QR 코드를 생성하시겠습니까?')) return;
    
    callAPI('generateTableQR', {tableId: tableId}, function(res) {
        alert(res.message || '처리되었습니다.');
        if (res.success) {
            // 현재 선택된 매장의 테이블 목록 다시 로드
            let storeId = $('#qrStoreSelect').val();
            if (storeId) {
                loadStoreTables();
            }
        }
    });
}

function downloadTableQR(tableId, storeName, tableNo, qrPath) {
    console.log('테이블 QR 다운로드 - 매장:', storeName, '테이블:', tableNo, 'QR 경로:', qrPath);
    
    let encodedQRPath = encodeURIComponentSafe(qrPath);
    let qrImageUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=500x500&data=' + encodedQRPath;
    
    let link = document.createElement('a');
    link.href = qrImageUrl;
    link.download = 'QR_' + storeName + '_테이블_' + tableNo + '_' + tableId + '.png';
    link.target = '_blank';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    
    alert('QR 코드 다운로드가 시작되었습니다: ' + storeName + ' - 테이블 ' + tableNo);
}

// Helper functions
function escapeQuotes(str) {
    return (str || '').replace(/'/g, "\\'").replace(/"/g, '\\"');
}

// 재귀 문제를 해결한 URL 인코딩 함수
function encodeURIComponentSafe(component) {
    // 기본 encodeURIComponent 사용 (재귀 방지)
    return encodeURIComponent(component);
}

// ==================== 매출 정보 ====================
function loadStoreListForSales() {
    callAPI('getStoreList', {}, function(res) {
        if (res && res.success) {
            let html = '<option value="">매장을 선택하세요</option>';
            res.data.forEach(function(store) {
                html += '<option value="' + store.storeId + '">' + store.storeName + '</option>';
            });
            $('#salesStoreSelect').html(html);
        }
    });
}

function loadStoreSales() {
    let storeId = $('#salesStoreSelect').val();
    let startDate = $('#startDate').val();
    let endDate = $('#endDate').val();
    
    if (!storeId) {
        alert('매장을 선택해주세요.');
        return;
    }
    
    if (!startDate || !endDate) {
        alert('날짜를 선택해주세요.');
        return;
    }
    
    callAPI('getStoreSalesInfo', {
        storeId: storeId,
        startDate: startDate,
        endDate: endDate
    }, function(res) {
        if (res && res.success) {
            let report = res.data;
            
            $('#totalSales').text('₩' + (report.totalSales || 0).toLocaleString());
            $('#totalOrders').text((report.totalOrders || 0).toLocaleString());
            $('#avgOrderAmount').text('₩' + (report.averageOrderAmount || 0).toLocaleString());
            
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
            
            $('#salesStats').removeClass('hidden');
        } else {
            alert('매출 정보를 불러올 수 없습니다.');
        }
    });
}
</script>
</body>
</html>