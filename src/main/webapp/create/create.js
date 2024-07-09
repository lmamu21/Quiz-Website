let questionCount = 1;
let answerChoices = new Map();

function addQuestion() {
    const questions = document.getElementById('questions');
    const questionType = document.getElementById('question-type').value;

    const questionBlock = document.createElement('div');
    questionBlock.className = 'question-block';
    questionBlock.id = `question-${questionCount}`;

    const indexDiv = document.createElement('div');
    indexDiv.textContent = `Question ${questionCount}`;
    indexDiv.style.fontWeight = 'bold';
    indexDiv.style.marginBottom = '10px';
    indexDiv.style.color = '#fff'; // Ensure the text color contrasts with the background

    questionBlock.appendChild(indexDiv);

    const removeButton = document.createElement('button');
    removeButton.type = 'button';
    removeButton.textContent = 'Remove Question';
    removeButton.classList.add('btn', 'red');
    removeButton.onclick = function () {
        questionBlock.remove();
        updateQuestionIndices();
    };

    questionBlock.appendChild(removeButton);

    if (questionType === 'multiple-choice question' || questionType === 'question-response' ) {
        const questionInput = document.createElement('input');
        questionInput.type = 'text';
        questionInput.className = 'question-text';
        questionInput.placeholder = 'Enter your question here';
        questionInput.name = `question-${questionCount}`;

        questionInput.required = true;

        questionBlock.appendChild(questionInput);
    }
    const questionTypeInput = document.createElement('input');
    questionTypeInput.name = `question-${questionCount}-type`;
    questionTypeInput.type = 'hidden';
    questionTypeInput.value = (questionType === 'multiple-choice question' ? 'multiple-choice' : (questionType === 'question-response' ? questionType : (questionType === 'image-response question' ? 'image-response' : 'fill-in-the-blank')));

    questionBlock.appendChild(questionTypeInput);

    if (questionType === 'multiple-choice question') {

    } else if (questionType === 'question-response') {

    } else if (questionType === 'image-response question') {
        const imageUrlInput = document.createElement('input');
        imageUrlInput.type = 'url';
        imageUrlInput.placeholder = 'Enter image URL';
        imageUrlInput.name = `question-${questionCount}-image-url`;
        imageUrlInput.required = true;
        questionBlock.appendChild(imageUrlInput);
    } else if (questionType === 'fill in the blank') {
        // No additional fields needed for fill-in-the-blank
        const before = document.createElement('input');
        before.type = 'text';
        before.className = 'question-text';
        before.placeholder = 'Statement before blank';
        before.name = `question-${questionCount}-before`;

        const after = document.createElement('input');
        after.type = 'text';
        after.className = 'question-text';
        after.placeholder = 'Statement after blank';
        after.name = `question-${questionCount}-after`;
        questionBlock.appendChild(before);
        questionBlock.appendChild(after);
    }

    const addChoiceButton = document.createElement('button');
    addChoiceButton.type = 'button';
    addChoiceButton.textContent = 'Add Answer Choice';
    answerChoices.set(questionCount, 1); // Initialize answer count for this question

    addChoiceButton.onclick = function () {
        const parentDivId = this.parentElement.id;
        let currentQuestion = parseInt(parentDivId.substring(9));
        let answerCount = answerChoices.get(currentQuestion); // Get the current answer count
        const answerInput = document.createElement('input');
        answerInput.type = 'text';
        answerInput.placeholder = 'Enter an answer choice';
        answerInput.name = `question-${currentQuestion}-choice-${answerCount}`;
        answerInput.required = true;

        if (questionType === 'multiple-choice question') {
            const indexDiv = document.createElement('span');
            indexDiv.textContent = String.fromCharCode(answerCount + 'A'.charCodeAt(0) - 1);
            questionBlock.insertBefore(indexDiv, addChoiceButton);


            const correctChoiceCheckbox = document.createElement('input');
            correctChoiceCheckbox.type = 'checkbox';
            correctChoiceCheckbox.name = `question-${currentQuestion}-answer-${answerCount}-isCorrect`;
            correctChoiceCheckbox.value = `question-${currentQuestion}-answer-${answerCount}-isCorrect`;
            questionBlock.insertBefore(correctChoiceCheckbox, addChoiceButton);
        }
        questionBlock.insertBefore(answerInput, addChoiceButton);

        answerChoices.set(currentQuestion, answerCount + 1); // Increment the answer count
    };

    questionBlock.appendChild(addChoiceButton);
    const markLabel=document.createElement('label');
    markLabel.for=`question-${questionCount}-mark`
    markLabel.textContent='Mark: ';
    questionBlock.appendChild(markLabel);

    const markInput = document.createElement('input');
    markInput.type='number';
    markInput.name=`question-${questionCount}-mark`;
    markInput.id=`question-${questionCount}-mark`;
    questionBlock.appendChild(markInput);
    questions.appendChild(questionBlock);

    questionCount++;
}

function updateQuestionIndices() {
    const questionBlocks = document.querySelectorAll('.question-block');
    questionBlocks.forEach((block, index) => {
        const questionIndex = index + 1;
        block.id = `question-${questionIndex}`;
        const indexDiv = block.querySelector('div');
        indexDiv.textContent = `Question ${questionIndex}`;

        const inputs = block.querySelectorAll('input');
        inputs.forEach(input => {
            const nameParts = input.name.split('-');
            if (nameParts[0] === 'question') {
                nameParts[1] = questionIndex;
                input.name = nameParts.join('-');
            }
        });

        answerChoices.set(questionIndex, 1); // Reset the answer count for the updated question index
    });
    questionCount = questionBlocks.length + 1;
}
