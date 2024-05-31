import React from 'react';
import './Job.css';

function JobInfoItem({ title, content }) {
    return (
        <div className="job-info-item">
            <span className="job-info-title">{title}:</span>
            <span className="job-info-content">{content}</span>
        </div>
    );
}

export default JobInfoItem;