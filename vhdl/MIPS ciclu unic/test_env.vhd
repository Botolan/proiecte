library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity numarator is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR(2 downto 0);
           sw : in STD_LOGIC_VECTOR(15 downto 0);
           led: out STD_LOGIC_VECTOR(15 downto 0);
           an : out STD_LOGIC_VECTOR(3 downto 0);
           cat : out STD_LOGIC_VECTOR(6 downto 0));
end numarator;

architecture Behavioral of numarator is
SIGNAL enablePC, enableJump, enableBranch: STD_LOGIC;
SIGNAL instructiune, rs, rt, ext, afisare, adresa, rezultat, branchadd, MemOut, outRam, mux_memtoreg, jumpAddress: STD_LOGIC_VECTOR(15 downto 0);
SIGNAL opcode, func: STD_LOGIC_VECTOR(2 downto 0);

SIGNAL RegDest, ExtOp, ALUSrc, Jump, Branch, MemWrite, MemtoReg, RegWrite, sa, zero, branchEnable: STD_LOGIC;
SIGNAL ALUOp : STD_LOGIC_VECTOR(2 downto 0);

begin

    jumpAddress <= "000" & instructiune(12 downto 0);

    MPG1: entity work.mpg port map(btn => btn(0), clk => clk, enable => enablePC);
    SSD: entity work.SSD port map(clk => clk, q => afisare, an => an, cat => cat);
    
    Instruction: entity work.instruction port map(clk => clk, enable => enablePC, JumpAddress => jumpAddress, Jump => Jump, BranchAddress => branchadd, PCSrc => branchEnable, PC1 => adresa, Instruction => instructiune);
    Decoder: entity work.ID port map(enable => enablePC, clk => clk, Instruction => instructiune, WD => mux_memtoreg, RegWrite => RegWrite, RegDest => RegDest, ExtOp => ExtOp, RS => rs, RT => rt, Ext_Imm => ext, func => func, sa => sa);
    
    
    Ex: entity work.EX port map(PC1 => adresa, RD1 => RS, ALUSrc => ALUSrc, RD2 => RT, Ext_imm => ext, sa => sa, func => func, ALUOp => ALUOp, BranchAddress => branchadd, Zero => zero, ALURes1 => rezultat);
    Mem: entity work.Mem port map(MemWrite => MemWrite, clk => clk, AluIn => rezultat, RD2 => RT, MemData => MemOut, AluOut => outRam);


    branchEnable <= zero and Branch;

    mux_memtoreg <= MemOut when MemtoReg = '1' else
                    outRam when MemtoReg = '0';
                     
    afisare <= instructiune when sw(2 downto 0) = "000" else
               adresa when sw(2 downto 0) = "001" else
               rs when sw(2 downto 0) = "010" else
               rt when sw(2 downto 0) = "011" else
               rezultat when sw(2 downto 0) = "100" else
               "1111111111111111";
               
    opcode <= instructiune(15 downto 13);
               
    led(15 downto 13) <= ALUOp;
    led(12 downto 10) <= func;
    led(0) <= RegDest;
    led(1) <= ExtOp;
    led(2) <= ALUSrc;
    led(3) <= Branch;
    led(4) <= Jump;
    led(5) <= MemWrite;
    led(6) <= Memtoreg;
    led(7) <= RegWrite;
    
    process(opcode)
    begin
       RegDest <= '0';
       ExtOp <= '0';
       ALUSrc <= '0';
       Branch <= '0';
       Jump <= '0';
       MemWrite <= '0';
       MemtoReg <= '0';
       RegWrite <= '0';
       ALUOp <= "000";
       if opcode = "000" then
           RegWrite <= '1';
           RegDest <= '1';
           ALUOp <= "000";
       else if opcode = "001" then
           ExtOp <= '1';
           AluSrc <= '1';
           RegWrite <= '1';
           ALUOp <= "001";
       else if opcode = "010" then
           ExtOp <= '1';
           AluSrc <= '1';
           RegWrite <= '1';
           MemtoReg <= '1';
           AlUOp <= "010";
       else if opcode = "011" then
           ExtOp <= '1';
           Branch <= '1';
           ALUOp <= "011";
       else if opcode = "100" then
           ExtOp <= '1';
           Jump <= '1';
           ALUOp <= "100";
       else if opcode = "101" then
           ExtOp <= '1';
           AluSrc <= '1';
           MemWrite <= '1';
           AlUOp <= "010";
       else if opcode = "110" then
           ExtOp <= '1';
           AluSrc <= '1';
           AluOp <= "101";
           RegWrite <= '1';
       else 
           ExtOp <= '1';
           AluSrc <= '1';
           AluOp <= "110";
           RegWrite <= '1'; 
       end if;      
       end if;
       end if;
       end if;
       end if;
       end if;
       end if;
    end process;
               
end Behavioral;
