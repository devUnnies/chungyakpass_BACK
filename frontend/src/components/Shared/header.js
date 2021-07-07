import { React } from "react";
import "./header.css";
import logo from "../../assets/logo.svg";
import data from "../../category.json";

function Login() {
  return (
    <div className="login">
      <div className="login-container">
        <div className="login-subcontainer">
          <div className="login-buttonArea">
            <div className="login-loginButton">로그인</div>
            <div className="login-signUpButton">회원가입</div>
          </div>
        </div>
      </div>
    </div>
  );
}

function Logo() {
  return (
    <div className="logo">
      <div className="logo-container">
        <div className="logo-image">
          <img src={logo} alt="logo"></img>
        </div>
        <div className="logo-name">
          청약<i>pass</i>
        </div>
      </div>
    </div>
  );
}

function Nav() {
  return (
    <div className="nav">
      <div className="nav-container">
        {/** 여러 개의 카테고리를 관리 */}
        <ul className="nav-items">
          {data.category.map((content, i) => {
            return (
              <li className="nav-item">
                <a link={content.link}>{content.name}</a>
                <ul className="nav-subItems">
                  {content.subcategory.map((subcontent, j) => {
                    return (
                      <li className="nav-subItem">
                        <a link={subcontent.link}>{subcontent.name}</a>
                      </li>
                    );
                  })}
                </ul>
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
}

const header = () => {
  return (
    <div>
      <Login></Login>
      <Logo></Logo>
      <Nav></Nav>
    </div>
  );
};

export default header;
