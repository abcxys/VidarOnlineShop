function addCustomer() {
    const newCustomer = document.getElementById('new-customer').value;
    if (newCustomer) {
        const li = document.createElement('li');
        li.textContent = newCustomer;
        li.draggable = true;
        document.getElementById('waiting-list').appendChild(li);
        document.getElementById('new-customer').value = '';
        addDragEvents(li);
    }
}

function addDragEvents(item) {
    item.addEventListener('dragstart', () => {
        item.classList.add('dragging');
    });

    item.addEventListener('dragend', () => {
        item.classList.remove('dragging');
    });

    // Touch events for mobile support
    item.addEventListener('touchstart', handleTouchStart);

    item.addEventListener('touchend', handleTouchEnd);

    item.addEventListener('touchmove', handleTouchMove);
}

function handleTouchStart(e) {
    const item = e.target;
    item.classList.add('dragging');
    item.initialTouchY = e.touches[0].clientY;
    item.initialTouchX = e.touches[0].clientX;
    item.initialElementY = item.offsetTop;
    item.initialElementX = item.offsetLeft;
}

function handleTouchMove(e) {
    e.preventDefault();
    const item = e.target;
    const touch = e.touches[0];
    const newY = touch.clientY - item.initialTouchY + item.initialElementY;
    const newX = touch.clientX - item.initialTouchX + item.initialElementX;
    item.style.position = 'absolute';
    item.style.top = `${newY}px`;
    item.style.left = `${newX}px`;
}

function handleTouchEnd(e) {
    const item = e.target;
    item.classList.remove('dragging');
    item.style.position = 'static';
    const touch = e.changedTouches[0];
    const targetElement = document.elementFromPoint(touch.clientX, touch.clientY);
    if (targetElement && targetElement.tagName === 'UL') {
        targetElement.appendChild(item);
    } else if (targetElement && targetElement.closest('ul')) {
        targetElement.closest('ul').appendChild(item);
    }
}

function getDragAfterElement(container, y) {
    const draggableElements = [...container.querySelectorAll('li:not(.dragging)')];

    return draggableElements.reduce((closest, child) => {
        const box = child.getBoundingClientRect();
        const offset = y - box.top - box.height / 2;
        if (offset < 0 && offset > closest.offset) {
            return { offset: offset, element: child };
        } else {
            return closest;
        }
    }, { offset: Number.NEGATIVE_INFINITY }).element;
}

function toggleServedColumn() {
    const servedColumn = document.getElementById('served');
    const showButton = document.querySelector('.show-button');
    if (servedColumn.style.display === 'none') {
        servedColumn.style.display = 'flex';
        showButton.style.display = 'none';
    } else {
        servedColumn.style.display = 'none';
        showButton.style.display = 'block';
    }
}

$(document).ready(function() {


    const columns = document.querySelectorAll('.column ul');

    columns.forEach(column => {
        column.addEventListener('dragover', e => {
            e.preventDefault();
            const afterElement = getDragAfterElement(column, e.clientY);
            const draggingItem = document.querySelector('.dragging');
            if (afterElement == null) {
                column.appendChild(draggingItem);
            } else {
                column.insertBefore(draggingItem, afterElement);
            }
        });

        // Touch events for mobile support
        column.addEventListener('touchmove', e => {
            e.preventDefault();
            const touchLocation = e.targetTouches[0];
            const afterElement = getDragAfterElement(column, touchLocation.clientY);
            const draggingItem = document.querySelector('.dragging');
            if (afterElement == null) {
                column.appendChild(draggingItem);
            } else {
                column.insertBefore(draggingItem, afterElement);
            }
        });
    });



    document.querySelectorAll('.column ul li').forEach(addDragEvents);
})

