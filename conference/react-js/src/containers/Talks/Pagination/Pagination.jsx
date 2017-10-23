import React from 'react';
import { PropTypes } from 'prop-types';

const Pagination = ({
  onChangeQuantityTalks,
  onChangeCurrentPage,
  quantityTalks,
  currentPage }) =>
  (<div className="pagination">
    <div className="pagination__left-side">
      <div className="pagination__item-wrapper">
        <div className="pagination__item pagination__item_fast-back" />
        <div
          tabIndex="-1"
          role="button"
          onClick={onChangeCurrentPage}
          className="pagination__item pagination__item_back"
        />
        <div className="pagination__item pagination__item_current">
          {currentPage}
        </div>
        <div
          role="button"
          tabIndex="-2"
          onClick={onChangeCurrentPage}
          className="pagination__item pagination__item_forward"
        />
        <div
          className="pagination__item
                  pagination__item_fast-forward"
        />
      </div>
      <select
        onChange={onChangeQuantityTalks}
        className="pagination__select"
        value={quantityTalks}
      >
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="20" defaultValue={20}>20</option>
        <option value="50">50</option>
        <option value="100">100</option>
      </select>
      <div className="pagination__per-page">
        Items per page
      </div>
    </div>
    <div className="pagination__right-side">
      <p className="pagination__navi">1 - 4 of 4 items</p>
    </div>
  </div>);

Pagination.propTypes = {
  quantityTalks: PropTypes.string.isRequired,
  currentPage: PropTypes.number.isRequired,
  onChangeQuantityTalks: PropTypes.func.isRequired,
  onChangeCurrentPage: PropTypes.func.isRequired,
};

export default Pagination;
