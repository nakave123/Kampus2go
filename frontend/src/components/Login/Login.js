import axios from "axios";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { authActions } from "../../store/auth_slice";
//import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import AuthService from "../../utilities/AuthService";
import { Buffer } from "buffer";
// import classes from  "./Login.module.scss";

//Created Login Function to handle login of recruiter and student
export default function Login() {
  const dispatch = useDispatch();
  const [user, setUser] = useState(null);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setError] = useState(false);
  const [success, setSuccess] = useState(false);
  const [loginAs, setLoginAs] = useState("Student");
  //const nav = useNavigate();

  //Login Handler gets called on click of login button
  const loginHandler = async (event) => {
    event.preventDefault();
    const str = username + ":" + password;
    const encodedCredential = Buffer.from(str).toString('base64');
    //const encodedCredential = window.btoa(str);
    const authorizationField = "Basic " + encodedCredential;
    console.log(encodedCredential);
    console.log(authorizationField);
    const headers = {
      "Access-Control-Allow-Origin": "http://localhost:3000",
      "Access-Control-Allow-Headers": "X-Requested-With,Content-Type,Accept,Origin",
      "Content-Type": "application/json; charset=utf-8",
      "Access-Control-Allow-Methods": "GET, OPTIONS",
      "Access-Control-Allow-Credentials": "true"
      };
    try {
      console.log("loginAs", loginAs);
      if (loginAs === "Student") {
        const response = await axios.get("https://demo.nakavepratik.me/v1/user/self", {
          auth: {
            username:username,
            password:password
          }
          //headers:headers
        });
        console.log(response.data);
        setUser(response.data);
        //setting user to local storage
        AuthService.setCurrUser(response.data);
        dispatch(authActions.login(response.data));
        //nav(`/dashboard-student/${response.data._id}`); //redirecting to student dashboard on successful login
      } else if (loginAs === "Recruiter") {
        const response = await axios.get("https://demo.nakavepratik.me/v1/user/self", {
          auth: {
            username:username,
            password:password
          }
        });
        //setting user to local storage
        console.log(response.data);
        setUser(response.data);
        AuthService.setCurrUser(response.data);
        dispatch(authActions.login(response.data));
        //nav(`/dashboard-recruiter/${response.data._id}`); // redirecting to recruiter dashboard on successful login
      }
    } catch (error) { //error handler
      let currErr =
        error &&
        error.response &&
        error.response.data &&
        error.response.data.message;
      setError(currErr);
      console.log(error.message);
    }
  };

  //method called on changing the drop down to login as student or recruiter
  const handleChange = (event) => {
    setLoginAs(event.target.value);
  };

  return (
    <div className="container">
      {/* TODO redirect to either dashboard or profile page */}

      <div className="welcome-container">
        <div className="wrapper">
          <h1 className="heading-secondary">
            Welcome to <span className="lg">Kampus2go</span>
          </h1>{" "}
          <p className="text-mute">
            Not a member?
            <br></br>
            <a href="/signup-student">Sign up as Mentee</a> &nbsp; &nbsp;
            <a href="/v1/user">Sign up as Mentor</a>
          </p>
        </div>
        <div className="signup-container">
          <h1 className="heading-primary">
            Log in<span className="span-blue">.</span>
          </h1>
          <p className="text-mute">
            Enter your credentials to access your account.
          </p>
          <form className="signup-form">
            <label className="inp">
              <select className="input-text" onChange={handleChange}>
                <option value="Student">Mentee</option>
                <option value="Recruiter">Mentor</option>
              </select>
              <span className="label">Login as : </span>
              <span className="input-icon"></span>
            </label>

            <label className="inp">
              <input
                type="text"
                className="input-text"
                placeholder="&nbsp;"
                onChange={(e) => setUsername(e.target.value)}
              />
              <span className="label">Username</span>
              <span className="input-icon"></span>
            </label>
            <label className="inp">
              <input
                type="password"
                className="input-text"
                placeholder="&nbsp;"
                id="password"
                onChange={(e) => setPassword(e.target.value)}
              />
              <span className="label">Password</span>
              <span
                className="input-icon input-icon-password"
                data-password
              ></span>
            </label>
            <p className="errorMessage">{errorMessage}</p>
            <button className="btn btn-login" onClick={loginHandler}>
              Login
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

// export default Login;
