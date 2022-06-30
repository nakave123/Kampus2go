import "./App.scss";
import Login from "./components/Login/Login";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import SignUpStudent from "./components/Login/SignUpStudent";
import RecruiterSignUp from "./components/Login/RecruiterSignUp";
import { useSelector, useDispatch } from "react-redux";
//import { useNavigate} from "react-router-dom";
import { useState, useEffect } from "react";
import AuthService from './utilities/AuthService';
import {authActions} from './store/auth_slice';
import UserProfile from "./components/UserProfile/UserProfile";

function App() {
  const isAuth = useSelector((state) => state.auth.isAuthenticated);
  const applications = useSelector((state) => state.applications.applications);
  const registrations = useSelector(
    (state) => state.registrations.registrations
  );
  const dispatch = useDispatch();
  console.log(isAuth, "isAuth");

  let user = useSelector((state) => state.auth.user);


  const checkUser = () => {
    //if user not in store
    if (!user) {
      user = AuthService.getCurrUser();
      
      //if user not in persistent local store
      if(!user)
      {
        return;
      }
      //add user to store
       dispatch(authActions.login(user));
    }


  };

  useEffect(() => {
    checkUser();
  }, []);


  return (
    <div className="prbg">
      <Router>
        <div className="app-state">
           {/* routes for login */}
          <Routes>
            <Route
              path="/"
              element={!isAuth ? <Login /> : ( user.student ? <Navigate to={`/dashboard-student/${user.student._id}`} /> : <Navigate to={`/dashboard-recruiter/${user.recruiter._id}`} />  )}
            ></Route>
            <Route path="/signup-student" element={<SignUpStudent />}></Route>
            {/* route for recruiter sign up page */}
            <Route
              path="/v1/user"
              element={<RecruiterSignUp />}
            ></Route>
            <Route
              path="/v1/user-profile"
              element={<UserProfile />}
            ></Route>
          </Routes>
        </div>
      </Router>
    </div>
  );
}


export default App;
