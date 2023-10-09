import React, { useState } from "react";

export const SearchResult = ({ result, itemsPerPage }) => {
  const [currentPage, setCurrentPage] = useState(1);

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = result.slice(indexOfFirstItem, indexOfLastItem);

  const getTableHeaders = () => {
    if (currentItems.length === 0) {
      return null;
    }
    return Object.keys(currentItems[0]).map((key, index) => (
      <th key={index}>{key}</th>
    ));
  };

  const getTableRows = () => {
    return currentItems.map((data, index) => (
      <tr key={index}>
        {Object.keys(data).map((key, innerIndex) => (
          <td key={innerIndex}>{data[key]}</td>
        ))}
      </tr>
    ));
  };

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const pageNumbers = [];
  for (let i = 1; i <= Math.ceil(result.length / itemsPerPage); i++) {
    pageNumbers.push(i);
  }

  return (
    <div>
      {currentItems.length === 0 ? (
        <p>No results found.</p>
      ) : (
        <div>
          <table>
            <thead>
              <tr>{getTableHeaders()}</tr>
            </thead>
            <tbody>{getTableRows()}</tbody>
          </table>
          <div className="pagination">
            {pageNumbers.map((number) => (
              <button
                key={number}
                className="page-button"
                onClick={() => paginate(number)}
              >
                {number}
              </button>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};
