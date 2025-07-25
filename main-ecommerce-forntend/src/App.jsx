import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SnbApp from './pages/snb/snbApp';


const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/*" element={<SnbApp />} />
      </Routes>
    </Router>
  );
};

export default App;
