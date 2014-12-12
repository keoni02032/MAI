parents('Барковский Андрей Андреевич', 'Барковский Андрей Геннадьевич', 'Барковская Елена').
parents('Барковская Елена', 'Еленкин Иван', 'Еленкина Алла').
parents('Еленкин Пётр Иванович', 'Еленкин Иван', 'Еленкина Алла').
parents('Барковский Сергей', 'Барковский Геннадий', 'Барковская Галина').
parents('Барковский Андрей Геннадьевич', 'Барковский Геннадий', 'Барковская Галина').
parents('Еленкин Александр', 'Еленкин Пётр Иванович', 'Еленкина Ольга').
parents('Еленкина Мария', 'Еленкин Пётр Иванович', 'Еленкина Ольга').
parents('Еленкин Серафим', 'Еленкин Александр', 'Еленкина Евгения').
parents('Еленкин Иван', 'Еленкин Пётр', 'Еленкина Фёкла').
parents('Еленкина Алла', 'Кузнецов Георгий', 'Кузнецова Анна').
parents('Барковская Галина', 'Казачков Иван', 'Казачкова Валентина').
parents('Барковский Геннадий', 'Барковский Григорий', 'Барковская Юлия').

male('Кузнецов Георгий').
male('Барковский Григорий').
male('Барковский Геннадий').
male('Барковский Андрей Андреевич').
male('Барковский Сергей').
male('Еленкин Иван').
male('Казачков Иван').
male('Еленкин Александр').
male('Еленкин Пётр').
male('Еленкин Пётр Иванович').
male('Барковский Андрей Геннадьевич').
male('Еленкин Серафим').

dever(X, Ans):-
	parents(_, A, X), % A - муж и X - жена
	parents(A, B, C), % У мужа есть родители
	parents(Ans, B, C), % Которые являются родителями
	Ans \= A, male(Ans). % Брата
