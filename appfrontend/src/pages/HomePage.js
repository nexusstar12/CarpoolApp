import axios from "axios";

import SearchBar from "../components/SearchBar";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import Bar from "../components/Bar";
import React, { useState, useEffect } from "react";

function HomePage() {
  const [search, setSearch] = useState("");
  const [result, setResult] = useState("");

  const handleSearchSubmit = (hehe) => {
    setSearch(hehe);
  };

  useEffect(() => {
    axios.get("").then((response) => {
      setResult(response.data);
    });
  }, [search]);

  return (
    <div className="search-container">
      <SearchBar onSearch={handleSearchSubmit} />
    </div>
  );
}

export default HomePage;
