library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;



entity Instruction is
    Port(clk : in STD_LOGIC;
         enable : in STD_LOGIC;
         JumpAddress: in STD_LOGIC_VECTOR(15 downto 0);
         Jump : in STD_LOGIC;
         BranchAddress : in STD_LOGIC_VECTOR(15 downto 0);
         PCSrc : in STD_LOGIC;
         PC1: out STD_LOGIC_VECTOR(15 downto 0);
         Instruction : out STD_LOGIC_VECTOR(15 downto 0));
end Instruction;

architecture Behavioral of Instruction is
type ROM is array(0 to 255) of STD_LOGIC_VECTOR(15 downto 0);
SIGNAL memorie_instructiune : ROM := (
0=> b"0000010010010110",
1=> b"0000100100100110",
2=> b"0000110110110110",
3=> b"0010000110000001",
4=> b"0010000100010100",
5=> b"0000100010010000",
6=> b"0000100110100001",
7=> b"0110000100000001",
8=> b"1000000000000101",
9=> b"1010000010000001",
10=> b"0100000010000001",
11=> b"0010010010000000",
others => x"9999");
SIGNAL PC : STD_LOGIC_VECTOR(15 downto 0) := x"0000";

SIGNAL mux1: STD_LOGIC_VECTOR(15 downto 0);
SIGNAL mux2: STD_LOGIC_VECTOR(15 downto 0);

begin

    process(clk) 
    begin
        if rising_edge(clk) then
            if enable = '1' then
                PC <= mux2;
            end if;
        end if;
    end process;
    
    mux1 <= PC + 1 when PCSrc = '0' else
            BranchAddress when PCSrc = '1';
    
    mux2 <= mux1 when Jump = '0' else
            JumpAddress when Jump = '1';
    
    
    PC1 <= PC + 1;
    Instruction <= memorie_instructiune(conv_integer(PC));
    
    
end Behavioral;
