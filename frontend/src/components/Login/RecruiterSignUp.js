import axios from "axios";
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.scss";

//Recruiter sign up form. Gets called when signing up as a recruiter
function RecruiterSignUp() {
  //const [email, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [first_name, setFirstname] = useState("");
  const [last_name, setLastname] = useState("");
  // const [recruiterId, setRecruiterId] = useState("");
  const [username, setUsername] = useState("");
  const [recruiter, setRecruiter] = useState("");
  const [orgs, setOrgs] = useState([]);
  const [organization_id, setSelectedOrg] = useState("");
  const [error, setError] = useState("");
  const nav = useNavigate();

 //fetching organziations from DB to allow the recruiter to associate with an organization while signup 
  // useEffect(() => {
  //   const fetchOrganizations = async () => {
  //     await axios
  //       .get(`http://localhost:9000/organizations`)
  //       .then(async (res) => {
  //         // setOrgs(res.data);
  //         console.log("fetch orgs", res.data);
  //         let orgArr = [];
  //         res.data.map((org) => {
  //           let obj = { name: org.organizationName, id: org._id };
  //           orgArr.push(obj);
  //         });

  //         setSelectedOrg(orgArr && orgArr[0].id)
  //         setOrgs(orgArr);
  //       });
  //   };
  //   fetchOrganizations();
  // }, []);

  //post method get called when registering as a recruiter
  const handleSignUpRecruiter = async (e) => {
    e.preventDefault();
    
    try {
      const response = await axios.post("https://demo.nakavepratik.me/v1/user",
      {
        //username,
        first_name: first_name,
      last_name: last_name,
      username: username,
      password: password
        // recruiterId,
        //organization_id,
      });
      console.log(response.data);
      setRecruiter(response.data);
      nav(`/`);
    } catch (error) {
      let currErr = "Please enter all the fields in the form!";
      setError(currErr);
    }
  };

  //method gets called when recruiter selects the value from the drop down
  const handleOrganizationChange = (e) => {
    setSelectedOrg(e.target.value);
  };


  return (
    <div className="container">
      {/* TODO redirect to either dashboard or profile page */}
      <div>
        <div className="welcome-container"></div>

        <div className="signup-container">
          <h1 className="heading-primary">
            Sign Up as a Mentor<span className="span-blue">.</span>{" "}
          </h1>
          {/* <p className="text-mute">Enter your credentials to access your account.</p> */}

          <form className="signup-form">
            {/* <label className="inp">
              <input
                type="text" 
                className="input-text"
                placeholder="&nbsp;"
                onChange={(e) => setUsername(e.target.value)}
              />
              <span className="label">Username</span>

              <span className="input-icon"></span>
            </label> */}
            <label className="inp">
              <input
                type="text"
                className="input-text"
                placeholder="&nbsp;"
                id="firstname"
                onChange={(e) => setFirstname(e.target.value)}
              />
              <span className="label">FirstName</span>
              <span className="input-icon"></span>
            </label>
            <label className="inp">
              <input
                type="text"
                className="input-text"
                placeholder="&nbsp;"
                id="lastname"
                onChange={(e) => setLastname(e.target.value)}
              />
              <span className="label">LastName</span>
              <span className="input-icon"></span>
            </label>
            <label className="inp">
              <input
                type="text"
                className="input-text"
                placeholder="&nbsp;"
                id="username"
                onChange={(e) => setUsername(e.target.value)}
              />
              <span className="label">Email</span>
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
            {/* <label className="inp">
              <input
                type="text"
                className="input-text"
                placeholder="&nbsp;"
                id="recruiterId"
                onChange={(e) => setRecruiterId(e.target.value)}
              />
              <span className="label">Recruiter ID</span>
              <span className="input-icon"></span>
            </label> */}
            {/* <label className="inp">
              <select
                className="input-text"
                onChange={handleOrganizationChange}
              >
                {orgs &&
                  orgs.map((org) => (
                    <option value={org.id}>{org.name}</option>
                  ))}
              </select>
              <span className="label">Select Organization : </span>
              <span className="input-icon"></span>
            </label> */}
            <p className="errorMessage">{error}</p>
            <button className="btn btn-login" onClick={handleSignUpRecruiter}>
              Sign Up
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default RecruiterSignUp;
