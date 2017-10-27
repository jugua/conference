import React from 'react';
import { PropTypes } from 'prop-types';

const renderPaginationNavi = (
  currentPage,
  quantityTalks,
  quantityAllPages) => {
  const currentCountMaxPages = currentPage * quantityTalks;
  const maxCountPages = currentCountMaxPages > quantityAllPages ?
    quantityAllPages : currentCountMaxPages;
  const minCountPage = ((currentPage - 1) * quantityTalks) + 1;
  return (
    <p className="pagination__navi">
      {minCountPage}
- {maxCountPages} of {quantityAllPages} items
    </p>);
};

const Pagination = ({
  onChangeQuantityTalks,
  onChangeCurrentPage,
  quantityTalks,
  currentPage,
  quantityAllPages,
  fastForwardPages }) =>
  (<div className="pagination">
    <div className="pagination__left-side">
      <div className="pagination__item-wrapper">
        <div
          tabIndex="-1"
          role="button"
          onClick={fastForwardPages}
          className="pagination__item pagination__item_fast-back"
        />
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
          tabIndex="-1"
          role="button"
          onClick={fastForwardPages}
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
      {renderPaginationNavi(currentPage, quantityTalks, quantityAllPages)}
    </div>
  </div>);

Pagination.propTypes = {
  quantityTalks: PropTypes.number.isRequired,
  currentPage: PropTypes.number.isRequired,
  quantityAllPages: PropTypes.number.isRequired,
  onChangeQuantityTalks: PropTypes.func.isRequired,
  onChangeCurrentPage: PropTypes.func.isRequired,
  fastForwardPages: PropTypes.func.isRequired,
};

export default Pagination;
