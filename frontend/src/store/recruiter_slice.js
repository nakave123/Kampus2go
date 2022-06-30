import { createSlice } from '@reduxjs/toolkit';
import axios from "axios";

const recruiterSlice = createSlice({
    name: 'recruiter',
    initialState : {recruiter:{}},
    reducers : {
        setRecruiter(state,action) {
            state.recruiter = action.payload.recruiter;
        },
    },
});