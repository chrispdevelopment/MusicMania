<%-- 
    Document   : adminHome
    Created on : 18-Mar-2014, 20:50:36
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>

<div class="col-md-8">
    <div class="content adminHome">
        <div class="row items">
            <div class="col-md-4">
                <a href="<%=request.getContextPath()%>/admin/userManagment">
                <div>
                    <img class="image" src="<%=request.getContextPath()%>/assets/images/admin/userImage.png" class="img-responsive" alt="User Management">
                </div>
                <div class="imageText">
                    User Management
                </div>
                </a>
            </div>
            <div class="col-md-4">
                <a href="<%=request.getContextPath()%>/admin/newsManagment">
                <div>
                    <img class="image" src="<%=request.getContextPath()%>/assets/images/admin/newsImage.png" class="img-responsive" alt="News Management">
                </div>
                <div class="imageText">
                    News Management
                </div>
                </a>
            </div>
            <div class="col-md-4">
                <a href="<%=request.getContextPath()%>/admin/merchManagment">
                <div>
                    <img class="image" src="<%=request.getContextPath()%>/assets/images/admin/merchImage.png" class="img-responsive" alt="Merchandise Management">
                </div>
                <div class="imageText">
                    Merchandise Management
                </div>
                </a>
            </div>
        </div>
        <div class="row items">
            <div class="col-md-4">
                <a href="<%=request.getContextPath()%>/admin/artistManagment">
                <div>
                    <img class="image" src="<%=request.getContextPath()%>/assets/images/admin/artistsImage.png" class="img-responsive" alt="Artists Management">
                </div>
                <div class="imageText">
                    Artists/Bands Management
                </div>
                </a>
            </div>
            <div class="col-md-4">
                <a href="<%=request.getContextPath()%>/admin/eventsManagment">
                <div>
                    <img class="image" src="<%=request.getContextPath()%>/assets/images/admin/eventsImage.png" class="img-responsive" alt="Events Management">
                </div>
                <div class="imageText">
                    Events Management
                </div>
                </a>
            </div>
            <div class="col-md-4">
                <a href="<%=request.getContextPath()%>/admin/orderManagment">
                <div>
                    <img class="image" src="<%=request.getContextPath()%>/assets/images/admin/orderImage.png" class="img-responsive" alt="Releases Management">
                </div>
                <div class="imageText">
                    Order Management
                </div>
                </a>
            </div>
        </div>
    </div>
</div>
