import React, { useState, useEffect } from 'react';
import { DragDropContext, Draggable, Droppable } from 'react-beautiful-dnd';
import { Row, Col, Badge, Space, Tag, Input } from 'antd';

import AddIssue from './Issue/Add/AddIssue';
import Sorter from './Sorter/Sorter';
import Issue from './Issue/Issue';

import { notifications } from '../Notifications/notifications';

import { IssueStatusModel } from 'Models/IssueStatusModel';
import { IssuePriorityModel } from 'Models/IssuePriorityModel';
import { IssueTypeModel } from 'Models/IssueTypeModel';

import { request } from 'Lib/request.js';

const { Search } = Input;

const emptyColumns = IssueStatusModel;


const IssueBoard = (props) =>
{

	const {
		boardId,
	} = props;

	const [columns, setColumns] = useState(emptyColumns);
	const [users, setUsers] = useState([]);
	const [issues, setIssues] = useState([]);
	const [allIssues, setAllIssues] = useState([]);
	const [sortByPriority, setSortByPriority] = useState(false);
	const [sortByType, setSortByType] = useState(false);
	const [sortByAssignment, setSortByAssignment] = useState(false);

	const issuePrefix = 'ISSUE_';


	useEffect(() =>
		{
			getIssues();
			getUsers();
		},
		[]  // eslint-disable-line react-hooks/exhaustive-deps
	);


	useEffect(() =>
		{
			if (issues && columns)
			{
				let stateColumns = {};

				Object.keys(columns).forEach((columnKey) =>
				{
					stateColumns = {
						...stateColumns,
						[columnKey]: {
							...columns[columnKey],
							items: issues.filter(issue => issue.status === columnKey),
						},
					};
				});

				setColumns(stateColumns);
			}
		},
		[issues]  // eslint-disable-line react-hooks/exhaustive-deps
	);


	useEffect(() =>
		{
			let sortedIssues = [];
			const stateIssues = [...issues];

			if (sortByAssignment)
			{
				sortedIssues = stateIssues.sort((a, b) => a.assignee === null ? -1 : 1);
			}

			else
				sortedIssues = resetIssueOrder(stateIssues);

			setIssues(sortedIssues);
		},
		[sortByAssignment]  // eslint-disable-line react-hooks/exhaustive-deps
	);


	useEffect(() =>
		{
			let sortedIssues = [];
			const stateIssues = [...issues];

			if (sortByPriority)
			{
				sortedIssues = stateIssues.sort((a, b) =>
					IssuePriorityModel[a.priority].index >
						IssuePriorityModel[b.priority].index ? -1 : 1
				);
			}

			else
				sortedIssues = resetIssueOrder(stateIssues);

			setIssues(sortedIssues);
		},
		[sortByPriority]  // eslint-disable-line react-hooks/exhaustive-deps
	);


	useEffect(() =>
		{
			let sortedIssues = [];
			const stateIssues = [...issues];

			if (sortByType)
				sortedIssues = stateIssues.sort((a, b) => a.type === IssueTypeModel.BUG.value ? -1 : 1);

			else
				sortedIssues = resetIssueOrder(stateIssues);

			setIssues(sortedIssues);
		},
		[sortByType]  // eslint-disable-line react-hooks/exhaustive-deps
	);


	const resetIssueOrder = (issues) => issues.sort((a, b) => a.id > b.id ? 1 : -1);


	const getIssues = () =>
	{
		if (boardId)
		{
			request.get(
				'issue',
				{
					boardId: boardId,
				},
				(issues) =>
				{
					setIssues(issues);
					setAllIssues(issues);
				},
				(e) => console.error(e),
			);
		}
	}


	const getUsers = () =>
	{
		request.get(
			'user',
			{},
			(users) => setUsers(users),
			(e) => console.error(e),
		);
	}


	const onDragEnd = (result, columns, setColumns) =>
	{
		if (!result.destination) return;

		const { source, destination, draggableId } = result;

		if (source.droppableId !== destination.droppableId)
		{
			const issueId = Number(draggableId && draggableId.replace(issuePrefix, ''));

			if (issueId && !isNaN(issueId))
			{
				request.patch(
					'issue/status/' + issueId,
					{
						status: destination.droppableId,
					},
					() => {},
					(error) => notifications.showIssueUpdateError(),
					() => getIssues(),
				);

				const sourceColumn = columns[source.droppableId];
				const destColumn = columns[destination.droppableId];
				const sourceItems = [...sourceColumn.items];
				const destItems = [...destColumn.items];
				const [removed] = sourceItems.splice(source.index, 1);

				destItems.splice(destination.index, 0, removed);

				setColumns({
					...columns,
					[source.droppableId]: {
						...sourceColumn,
						items: sourceItems,
					},
					[destination.droppableId]: {
						...destColumn,
						items: destItems,
					},
				});
			}
		}

		else
		{
			const column = columns[source.droppableId];
			const copiedItems = [...column.items];
			const [removed] = copiedItems.splice(source.index, 1);

			copiedItems.splice(destination.index, 0, removed);

			setColumns({
				...columns,
				[source.droppableId]: {
					...column,
					items: copiedItems,
				},
			});
		}
	}


	const filterIssues = (searchValue) =>
	{
		setIssues(allIssues.filter((issue) =>
			{
				return issue.title.includes(searchValue) ||
					(issue.description && issue.description.includes(searchValue));
			})
		);
	}


	return (

		<>
			<Row
				style={{
					marginBottom: 40,
				}}
			>
				<Space>
					<AddIssue
						{...props}
						getIssues={getIssues}
						users={users}
					/>
					<Search
						placeholder='Find Issues...'
						onSearch={filterIssues}
						enterButton
						allowClear
					/>
				</Space>
				<div
					style={{
						marginLeft: 'auto',
						marginRight: 20,
					}}
				>
					<Sorter
						{...props}
						sortByPriority={sortByPriority}
						sortByType={sortByType}
						sortByAssignment={sortByAssignment}
						setSortByPriority={setSortByPriority}
						setSortByType={setSortByType}
						setSortByAssignment={setSortByAssignment}
					/>
				</div>
			</Row>
			<Row
				gutter={[36, 36]}
				style={{
					padding: '10px 30px 0 30px',
					minHeight: '90%',
					background: 'rgb(247, 247, 247)',
				}}
			>
				<DragDropContext
					onDragEnd={result => onDragEnd(result, columns, setColumns)}
				>
					{Object.entries(columns).map(([columnId, column], index) => {
	          			return (
							<Col
								style={{
									display: 'flex',
									flexDirection: 'column',
									alignItems: 'center',
									width: '20%',
								}}
								key={columnId}
							>
								<h4>
									<Space>
										<Tag
											color={column.titleTagColor}
											icon={column.icon}
										>
											{column.name}
										</Tag>
										{column && column.items &&
										<Badge
											count={column.items.length}
											showZero
											className='site-badge-count-4'
											style={{
												backgroundColor: '#fff',
												color: '#999',
												boxShadow: '0 0 0 1px #d9d9d9 inset',
											}}
										/>}
									</Space>
								</h4>
								<div
									style={{
										margin: 8,
										height: '100%',
									}}
								>
									<Droppable droppableId={columnId} key={columnId}>
										{(provided, snapshot) => {
											return (
												<div
													{...provided.droppableProps}
													ref={provided.innerRef}
													style={{
														background: snapshot.isDraggingOver ?
														'#f1ffea' : 'white',
														padding: 4,
														width: 300,
														height: '100%',
														minHeight: 120,
													}}
												>
		                        					{column.items.map((issue, index) => {
		                          						return (
								                            <Draggable
																key={issue.id}
																draggableId={issuePrefix + issue.id}
																index={index}
								                            >
																{(provided, snapshot) => {
																	return (
																		<Issue
																			{...props}
																			issue={issue}
																			provided={provided}
																			snapshot={snapshot}
																			getIssues={getIssues}
																			users={users}
																		/>
		                                							);
		                              							}}
															</Draggable>
														);
													})}
													{provided.placeholder}
												</div>
											);
										}}
									</Droppable>
								</div>
							</Col>
						);
					})}
				</DragDropContext>
			</Row>
		</>
	);
}


export default IssueBoard;
