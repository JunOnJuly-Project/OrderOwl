<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Order Owl</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="${pageContext.request.contextPath}/user/css/styles.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/user/css/modal.css" rel="stylesheet" />
    </head>
    <body>
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container px-4 px-lg-5">
                <a class="navbar-brand" href="#!">${store.storeName}
                </a>
              
                    <form class="d-flex">
                        <button class="btn btn-outline-dark orderModal" type="submit" id="testId">
                            <i class="bi-cart-fill me-1"></i>
                            장바구니
                            <span class="badge bg-dark text-white ms-1 rounded-pill" id="testCart">0</span>
                        </button>
                        
                          <button class="btn btn-outline-dark listModal" type="submit" id="testId">
                            <i class="bi-cart-fill me-1"></i>
                            주문내역
                            <span class="badge bg-dark text-white ms-1 rounded-pill" id="testCart"></span>
                        </button>
                    </form>
                    
                    
                </div>
            </div>
        </nav>
        <!-- Header-->
        <header class="bg-dark py-5">
            <div class="container px-4 px-lg-5 my-5">
                <div class="text-center text-white">
                    <h1 class="display-4 fw-bolder">${store.address}</h1>
               
                </div>
            </div>
        </header>
        <!-- Section-->
        <section class="py-5">
            <div class="container px-4 px-lg-5 mt-5">
                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                
                <c:forEach items="${menu}" var="item">
  
                    <div class="col mb-4" >
                        <div class="card h-100">
                            <!-- Product image-->
                            <img class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." />
                            <!-- Product details-->
                            <div class="card-body p-4">
                                <div class="text-center">
                                    <!-- Product name-->
                                    <h5 class="fw-bolder">${item.menuName}
                                    </h5>
                                    <!-- Product price-->
                                    가격 : ${item.price} 원
                                </div>
                            </div>
                            <!-- Product actions-->
                            <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                                <div class="text-center"><a class="btn btn-outline-dark mt-auto addModal" 
                       href="#"
                       data-menuname="${item.menuName}"
                       data-price="${item.price}">추가하기</a></div>
                            </div>
                        </div>
                    </div>
                     </c:forEach>
                    
                </div>
            </div>
            
            <footer class="py-5 bg-dark" id="testFooter">
            <div class="container"><p class="m-0 text-center text-white" id="testText">Order Req </p></div>
        </footer>
        </section>

  <!-- modal -->
    <div id="modal-overlay"></div>
    <div id="modal-window">
    <button id="close-button">&times;</button>
    <div id="modal-content"></div>
    </div>

        <!-- Footer-->
        <footer class="py-5 bg-dark">
            <div class="container"><p class="m-0 text-center text-white">Copyright &copy; Your Website 2023</p></div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="${pageContext.request.contextPath}/user/js/scripts.js"></script>
    </body>
</html>
