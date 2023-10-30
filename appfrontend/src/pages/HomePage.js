/* eslint-disable jsx-a11y/img-redundant-alt */
import SearchBar from "../components/SearchBar";
import React, { useState } from "react";
import axiosInstance from "../config/axios.config";
import { SearchResult } from "../components/SearchResult";

function HomePage() {
  const [search, setSearch] = useState("");
  const [result, setResult] = useState([]);

  const handleSearchSubmit = async ({ searchQuery, filterOption }) => {
    setSearch({ searchQuery, filterOption });

    const data = await axiosInstance.get(
      `/searchbar?filter=${filterOption}&value=${searchQuery}`
    );
    setResult(data.data);
  };

  return (
    <div className="home-page-container">
      <div className="image-container">
        <img
          src="homepage_carpool.jpg"
          style={{ width: "100%", height: "100%" }}
          alt="Description of the image"
        />
      </div>
      <div className="search-container">
        <h1 style={{ fontSize: "50px" }}>Get there, together.</h1>
        <SearchBar onSearch={handleSearchSubmit} />
        {result.length > 0 ? (
          <div className="search-results-container">
            <SearchResult
              className="search-result"
              result={result}
              itemsPerPage={10}
            />
          </div>
        ) : (
          <div className="search-results-empty"></div>
        )}
      </div>
    </div>
  );
}

export default HomePage;
