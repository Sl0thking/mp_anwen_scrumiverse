<div class="background">
	<div class="login">
        <form action="login_check.htm">
            <div class="form-group">
            	E-Mail:
                <input type="text" class="form-control" id="email" value="example@mail.com" onblur="if (this.value==' '){this.vlaue = 'example@mail.com';}" onfocus="if (this.value == 'example@mail.com') {this.value = ''}">
            </div>
            <div class="form-group">
            	Password:
                <input type="password" class="form-control" id="password" value="password" onblur="if (this.value==''){this.vlaue = 'password';}" onfocus="if (this.value == 'password') {this.value = ''}">
            </div>
            <div class="checkbox">
                <label><input type="checkbox"> Remember me</label>
            </div>
            <button type="submit" id="largebutton" class="btn btn-default">login</button>
            <p>Haven't an account yet? <a href="./register.htm">Register</a> now!</p>
        </form>
    </div>
</div>
<div class="navbar-inverse navbar-fixed-bottom">
    <p class="center">2016 - Scrumiverse - Team Atlas</p>
</div>