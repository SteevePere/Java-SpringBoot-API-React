import React from 'react';
import { Row, Col, Empty, Input, Space, Spin } from 'antd';

import AddBoard from '../Add/AddBoard';
import BoardCard from './BoardCard/BoardCard';
import ManageUsersModal from './ManageUsersModal/ManageUsersModal';

const { Search } = Input;


const BoardListView = (props) =>
{

	const {
		isAdmin,
		boards,
		boardToManage,
		boardsLoading,
		filterBoards,
	} = props;


	const noBoards = boards && boards.length === 0;


	return (

		<div
			style={{
				height: '100%',
				margin: '-10px 15px 0px 15px',
				overflowX: 'hidden',
				overflowY: 'auto',
			}}
		>
			<Row
				style={{
					padding: '30px 30px 10px 30px',
				}}
			>
				<Space>
					{isAdmin &&
					<div style={{ float: 'left' }}>
						<AddBoard
							{...props}
						/>
					</div>}
					<div style={{ float: 'left' }}>
						<Search
							placeholder='Find Boards...'
							onSearch={filterBoards}
							enterButton
							allowClear
						/>
					</div>
				</Space>
			</Row>
			<Row
				gutter={[36, 36]}
				style={{
					padding: '10px 30px 0 30px',
				}}
			>
				{boards && boards.map((board) =>
					<Col
						xl={8}
						lg={8}
						md={24}
						xs={24}
						key={'col_' + board.id}
					>
						<BoardCard
							key={board.id}
							board={board}
							{...props}
						/>
					</Col>
				)}
				{!boardsLoading && noBoards &&
				<Empty
					description='No Boards were found'
					style={{ margin: 'auto', marginTop: '25vh' }}
				/>}
				{boardsLoading &&
				<div
					style={{
						margin: 'auto',
						marginTop: '25vh',
					}}
				>
					<Spin/>
				</div>
				}
			</Row>
			{boardToManage &&
			<ManageUsersModal
				{...props}
			/>}
		</div>
	);
}


export default BoardListView;
