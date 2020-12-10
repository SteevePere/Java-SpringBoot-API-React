import React from 'react';
import { Input, Divider, Tooltip, Space } from 'antd';
import {
	AppstoreTwoTone,
} from '@ant-design/icons';


const BoardCardHeader = (props) =>
{

	const {
		board,
		boardToEdit,
		setBoardToEdit,
		handleBoardTitleInputChange,
		saveBoard,
	} = props;


	const editingBoard = boardToEdit && boardToEdit.id === board.id;


	return (

		<div
			style={{
				backgroundImage: 'url(' + window.location.origin + '/team_logo.jpg)',
				backgroundSize: 'cover',
				backgroundRepeat: 'no-repeat',
				backgroundPosition: '-20px -90px',
				boxShadow: 'inset 0 0 0 200px rgba(255, 255, 255, 0.65)',
			}}
		>
			<h3>
				<Space style={{ height: 40 }}>
					<AppstoreTwoTone/>
					{editingBoard ?
					<Input
						value={boardToEdit.title}
						onChange={handleBoardTitleInputChange}
						onBlur={saveBoard}
						onPressEnter={saveBoard}
					/> :
					<Tooltip
						title='Edit Title'
						mouseEnterDelay={1}
					>
						<span
							onClick={() => setBoardToEdit(board)}
							style={{ cursor: 'pointer' }}
						>
							{board.title}
						</span>
					</Tooltip>}
				</Space>
			</h3>
			<Divider/>
		</div>
	);
}


export default BoardCardHeader;
