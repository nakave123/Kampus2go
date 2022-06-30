//Created AuthService to get and retrieve user from the local storage
class AuthService {
  setCurrUser = (data) => {
    localStorage.setItem("user", JSON.stringify(data));
  };

  getCurrUser = () => {
    return JSON.parse(localStorage.getItem("user"));
  };

  removeCurrUser = () => {
    localStorage.removeItem("user");
  };
}

export default new AuthService();
