intersecteaza([], L2, []).
intersecteaza([H | T], L2, [H | R]) :- not(member(H, T)), member(H, L2), !, intersecteaza(T, L2, R).
intersecteaza([H | T], L2, R) :- intersecteaza(T, L2, R).


diff([], L1, []).
diff([H | T], L1, [H | R]) :- not(member(H, T)), not(member(H, L1)), !, diff(T, L1, R).
diff([H | T], L1, R) :- diff(T, L1, R).


min1([H|T], M) :- min1(T, M), M<H, !.
min1([H|_], H).

delete_all(X, [X|T], R) :- delete_all(X, T, R).
delete_all(X, [H|T], [H|R]) :- delete_all(X, T, R).
delete_all(_, [], []).


delete_minim(L, R) :- min1(L, MIN), delete_all(MIN, L, R), !.


reverse_list([], []).
reverse_list([H | T], [R | H]) :- reverse_list(T, R).

reverse1([], []).
reverse1([H|T], R) :- reverse1(T, Rcoada), append(Rcoada, [H], R).


reverse_k([], K, []).
reverse_k([H | T], K, [H | R]) :- K>0, !, reverse_k(T, K-1, R).
reverse_k([H | T], K, R) :- reverse1([H | T], R).

count_consecutiv([], 0).
count_consecutiv([H | T], EL, R) :- EL==H, count_consecutiv(T, EL, R1), R is R1 + 1.
count_consecutiv([H | T], EL, R) :- R is 1.

sari_peste([H | T], K, R) :- K>0, sari_peste(T, K-1, R).
sari_peste([H | T], K, R) :- R = [H | T].
sari_peste([], K, []).


rle_encode([H | T], R) :- count_consecutiv(T, H, COUNT), sari_peste([H | T], COUNT, R1), rle_encode(R1, R2),  R = [[H | R1] | R2].
rle_encode([], []).