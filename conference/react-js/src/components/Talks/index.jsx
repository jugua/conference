import React from 'react';
import { connect } from 'react-redux';
import FilterForm from '../../containers/Talks/FilterForm';
import DisplayTalks from '../../containers/Talks/DisplayTalks';

const TalksRender = () => (
  <div className="talks-container">
    <div className="talks">
      <div className="talks__header">
        <a className="btn talks__button">export to excel </a>
      </div>
      <FilterForm />
      <div className="data-table">
        <div className="table-header">
          <div className="table-header__item
                table-header__item_check-talk"
          >
            <input type="checkbox" />
          </div>
          <div className="table-header__item
                table-header__item_speaker-talk"
          >
                speaker
          </div>
          <div className="table-header__item
                table-header__item_title-talk"
          >
                title
          </div>
          <div className="table-header__item
                table-header__item_topic-talk"
          >
                topic
          </div>
          <div className="table-header__item table-header__item_date-talk">
                submitted date
          </div>
          <div className="table-header__item
                table-header__item_status-talk"
          >
                status
          </div>
          <div className="table-header__item
                table-header__item_comments-talk"
          >
                organizer comments
          </div>
          <div className="table-header__item
                table-header__item_assign-talk"
          >
                assigned to
          </div>
          <div className="table-header__item table-header__scroll-fix" />
        </div>
        <DisplayTalks />
      </div>
      <div className="pagination">
        <div className="pagination__left-side">
          <div className="pagination__item-wrapper">
            <div className="pagination__item pagination__item_fast-back" />
            <div className="pagination__item pagination__item_back" />
            <div className="pagination__item pagination__item_current">
                  1
            </div>
            <div className="pagination__item pagination__item_forward" />
            <div className="pagination__item pagination__item_fast-forward" />
          </div>
          <select className="pagination__select">
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option selected value="50">50</option>
            <option value="100">100</option>
          </select>
          <div className="pagination__per-page">
                  Items per page
          </div>
        </div>
        <div className="pagination__right-side">
          <p className="pagination__navi">1 - 4 of 4 items</p>
        </div>
      </div>
    </div>
  </div>
);

function mapStateToProps(state) {
  return {
    filter: state.filter,
  };
}

export default connect(mapStateToProps)(TalksRender);
