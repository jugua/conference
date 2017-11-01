import React, { Component } from 'react';
import { connect } from 'react-redux';
import { PropTypes } from 'prop-types';
import axios from 'axios';
import { bindActionCreators } from 'redux';

import {
  Table,
  TableHeader,
  TableBody,
  TableRow,
  TableRowColumn,
} from 'material-ui/Table';
import FilterForm from './FilterForm';
import TalksHeader from '../../components/Talks/TalksHeader/TalksHeader';
import Pagination from './Pagination/Pagination';
import loadData from '../../actions/load';
import action from '../../constants/actions-types';
import { topics, talk } from '../../constants/backend-url';

class Talks extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listOfTalks: [],
      filter: { topic: '', status: '' },
      listOfTopics: [],
      speaker: '',
      title: '',
      topic: '',
      status: '',
      comments: '',
      currentPage: 1,
      quantityTalks: 20,
      quantityAllPages: 0,
    };
  }

  componentDidMount() {
    const { LOAD } = action;
    axios.get(topics)
      .then(({ data }) => {
        this.setState({ listOfTopics: data });
      });

    axios.get(talk)
      .then(({ data }) => {
        this.props.load(LOAD, data);
        this.setState({ listOfTalks: data });
        this.doFilter();
        const countPages = Math.ceil(data.length / this.state.quantityTalks);
        this.setState({
          quantityAllPages: data.length,
          maxPages: countPages,
        });
      }).catch(() => {
        this.doFilter();
      },
      );
  }

  onChangeFilter = ({ target }) => {
    this.setState(prevState => ({
      filter: {
        ...prevState.filter,
        [target.name]: target.value,
      },
    }));
  };

  onChangeQuantityTalks = ({ target: { value } }) => {
    const { listOfTalks: { length } } = this.state;
    this.setState({
      quantityTalks: Number(value),
      currentPage: 1,
      maxPages: Math.ceil(length / Number(value)),
    },
    this.doFilter);
  };

  onChangeCurrentPage = ({ target: { classList: { value } } }) => {
    if (value.indexOf('back') > -1) {
      if (this.state.currentPage > 1) {
        this.setState(prevValue => (
          {
            ...prevValue,
            currentPage: Number(prevValue.currentPage) - 1,
          }
        ),
        this.doFilter,
        );
      }
    } else if (this.state.currentPage < this.state.maxPages) {
      this.setState(prevValue => (
        {
          ...prevValue,
          currentPage: Number(prevValue.currentPage) + 1,
        }
      ),
      this.doFilter,
      );
    }
  };

  getRows = (talks, columns) => (
    talks.map(element => (
      <TableRow key={element.id}>
        {this.renderTalksList(element, columns)}
      </TableRow>),
    )
  );

  fastForwardPages = ({ target: { classList: { value } } }) => {
    const { maxPages } = this.state;
    if (value.indexOf('forward') > -1) {
      this.setState({ currentPage: maxPages }, this.doFilter);
    } else {
      this.setState({ currentPage: 1 }, this.doFilter);
    }
  };

  doFilter = () => {
    const {
      filter,
      listOfTalks,
      currentPage,
      quantityTalks,
    } = this.state;
    const { APPLY_FILTERS } = action;
    this.props.load(APPLY_FILTERS,
      {
        filter,
        listOfTalks,
        page: currentPage,
        quantity: quantityTalks,
      });
  };

  handleResetFiltersClick = () => {
    this.setState({
      filter: { topic: '', status: '' },
    }, this.doFilter);
  };

  handleFilterClick = () => {
    this.doFilter();
  };

  sortTalks = ({ target: { tagName, dataset: { name } } }) => {
    if (tagName === 'TH' && name) {
      const { ASC, SORT_ALL_TALKS } = action;
      const { [name]: sortField } = this.state;
      const { load, talks } = this.props;
      const value = sortField === ASC ? '' : ASC;
      load(SORT_ALL_TALKS, { talks, direction: value, field: name });
      this.setState({ [name]: value });
    }
  };

  renderTalksList = (data, columns) => (
    columns.map((col) => {
      switch (col) {
      case 'id':
        return null;
      case 'name':
        return (
          <TableRowColumn key={col}>
            <a className="link">{data.name}</a>
          </TableRowColumn>
        );
      case 'title':
        return (
          <TableRowColumn key={col}>
            <a
              className="link"
              data-talk-id={data.id}
            >{data.title}</a>
          </TableRowColumn>
        );
      default:
        return (
          <TableRowColumn key={col}>
            {data[col]}
          </TableRowColumn>
        );
      }
    })
  );

  render() {
    const {
      listOfTalks,
      listOfTopics,
      quantityTalks,
      currentPage,
      quantityAllPages,
    } = this.state;
    const { columns, onClick } = this.props;
    return (

      <div
        className="talks tabs-container"
        onClick={onClick}
        role="menu"
        tabIndex="0"
      >
        <div className="talks__header">
          <a className="btn talks__button">export to excel </a>
        </div>
        <FilterForm
          topics={listOfTopics}
          onChangeFilter={this.onChangeFilter}
          handleFilterClick={this.handleFilterClick}
          handleResetFiltersClick={this.handleResetFiltersClick}
        />
        <Table
          selectable
          multiSelectable
        >
          <TableHeader
            displaySelectAll
            adjustForCheckbox
            enableSelectAll
          >
            <TalksHeader columns={columns} sortTalks={this.sortTalks} />
          </TableHeader>
          <TableBody showRowHover>
            {this.getRows(listOfTalks, columns)}
          </TableBody>
        </Table>
        <Pagination
          quantityAllPages={quantityAllPages}
          currentPage={currentPage}
          onChangeCurrentPage={this.onChangeCurrentPage}
          quantityTalks={quantityTalks}
          onChangeQuantityTalks={this.onChangeQuantityTalks}
          fastForwardPages={this.fastForwardPages}
        />
      </div>
    );
  }
}

Talks.propTypes = {
  load: PropTypes.func.isRequired,
  talks: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
  columns: PropTypes.arrayOf(PropTypes.string),
  onClick: PropTypes.func.isRequired,
};

Talks.defaultProps = {
  columns: [
    'id',
    'name',
    'title',
    'topic',
    'status',
    'comment',
  ],
};

const mapStateToProps = ({ talks, userTalks }) => (
  {
    talks,
    userTalks,
  });

const mapDispatchToProps = dispatch => ({

  load: bindActionCreators(
    loadData, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(Talks);
