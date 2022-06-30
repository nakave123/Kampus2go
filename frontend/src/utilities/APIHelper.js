import Axios from "axios";

const baseUrl = "http://localhost:9000";

// API's for different endpoints

async function getAllItems(endpoint) {
  const res = await Axios.get(baseUrl + "/" + endpoint);
  return res;
}

async function getItemById(endpoint, id) {
  const res = await Axios.get(baseUrl + "/" + endpoint + "/" + id);
  return res;
}

async function addItem(endpoint, item) {
  const res = await Axios.post(baseUrl + "/" + endpoint + "/", { item });
  return res;
}
async function updateItemById(endpoint, id, item) {
  const res = await Axios.put(baseUrl + "/" + endpoint + "/" + id, { item });
  return res;
}

async function deleteItemById(endpoint, id) {
  const res = await Axios.delete(baseUrl + "/" + endpoint + "/" + id);
  return res;
}

export default {
  getAllItems,
  getItemById,
  addItem,
  updateItemById,
  deleteItemById,
};
